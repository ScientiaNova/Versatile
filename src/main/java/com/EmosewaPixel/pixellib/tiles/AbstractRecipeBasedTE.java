package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.capabilities.ImprovedItemStackHandler;
import com.EmosewaPixel.pixellib.recipes.AbstractRecipeList;
import com.EmosewaPixel.pixellib.recipes.SimpleMachineRecipe;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.IntStream;

public abstract class AbstractRecipeBasedTE<T extends SimpleMachineRecipe> extends ProgressiveTE {
    private AbstractRecipeList<T, ?> recipeList;
    private T currentRecipe;

    public T getCurrentRecipe() {
        return currentRecipe;
    }

    public void setCurrentRecipe(T recipe) {
        currentRecipe = recipe;
    }

    public AbstractRecipeList<T, ?> getRecipeList() {
        return recipeList;
    }

    public int getSlotCount() {
        return combinedHandler.getSlots();
    }

    public AbstractRecipeBasedTE(TileEntityType type, AbstractRecipeList<T, ?> recipeList) {
        super(type);
        this.recipeList = recipeList;

        recipeInventory = new ImprovedItemStackHandler(recipeList.getMaxRecipeSlots(), (Integer[]) IntStream.range(0, recipeList.getMaxInputs()).mapToObj(Integer::new).toArray(), (Integer[]) IntStream.range(recipeList.getMaxInputs(), recipeList.getMaxRecipeSlots()).mapToObj(Integer::new).toArray()) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (!noInputSlots.contains(slot))
                    for (SimpleMachineRecipe recipe : recipeList.getReipes())
                        if (recipe.itemBelongsInRecipe(stack))
                            return true;

                return false;
            }

            @Override
            protected void onContentsChanged(int slot) {
                currentRecipe = getRecipeByInput();
                markDirty();
            }
        };

        combinedHandler = new CombinedInvWrapper(recipeInventory);
    }

    protected ImprovedItemStackHandler recipeInventory;

    protected CombinedInvWrapper combinedHandler;

    @Override
    public void tick() {
        if (!world.isRemote)
            if (getProgress() > 0) {
                if (!currentRecipe.isEmpty()) {
                    subtractProgress(1);
                    if (getProgress() == 0)
                        work();
                } else
                    setProgress(0);
            } else
                startWorking();
        markDirty();
    }

    protected void startWorking() {
        if (!currentRecipe.isEmpty())
            if (canOutput(currentRecipe, true))
                setProgress(currentRecipe.getTime() - 1);
    }

    protected void work() {
        SimpleMachineRecipe lastRecipe = currentRecipe;
        canOutput(lastRecipe, false);
        for (int i = 0; i < recipeList.getMaxInputs(); i++)
            recipeInventory.extractItem(i, lastRecipe.getInputCount(i), false);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("RecipeInventory"))
            recipeInventory.deserializeNBT((CompoundNBT) compound.get("RecipeInventory"));
        currentRecipe = getRecipeByInput();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("RecipeInventory", recipeInventory.serializeNBT());
        return compound;
    }

    public boolean canInteractWith(PlayerEntity playerIn) {
        return playerIn.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64D;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return LazyOptional.of(() -> recipeInventory).cast();
        return LazyOptional.empty();
    }

    protected boolean canOutput(SimpleMachineRecipe recipe, boolean simulate) {
        if (recipe.getAllInputs() == null)
            return false;

        boolean can = true;

        for (int i = 0; i < recipe.getAllOutputs().length; i++)
            if (!recipeInventory.insertItem(recipeList.getMaxInputs() + i, recipe.getOutput(i).copy(), simulate).isEmpty())
                can = false;

        return can;
    }

    public void dropInventory() {
        for (int i = 0; i < combinedHandler.getSlots(); i++) {
            ItemStack stack = combinedHandler.getStackInSlot(i);

            world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack));
        }
    }

    protected abstract T getRecipeByInput();
}
