package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.capabilities.ImprovedItemStackHandler;
import com.EmosewaPixel.pixellib.miscutils.ItemUtils;
import com.EmosewaPixel.pixellib.miscutils.StreamUtils;
import com.EmosewaPixel.pixellib.recipes.AbstractRecipeList;
import com.EmosewaPixel.pixellib.recipes.SimpleMachineRecipe;
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
import java.util.Random;
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

        recipeInventory = new ImprovedItemStackHandler(recipeList.getMaxRecipeSlots(), StreamUtils.getArrayFromRange(0, recipeList.getMaxInputs()), StreamUtils.getArrayFromRange(recipeList.getMaxInputs(), recipeList.getMaxRecipeSlots())) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return recipeList.getRecipes().stream().anyMatch(recipe -> recipe.itemBelongsInRecipe(stack));
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
            if (canOutput(currentRecipe))
                setProgress(currentRecipe.getTime() - 1);
    }

    protected void work() {
        SimpleMachineRecipe lastRecipe = currentRecipe;
        output(lastRecipe);
        Random rand = new Random();
        StreamUtils.repeat(recipeList.getMaxInputs(), i -> {
            if (lastRecipe.getOutputChance(i) >= rand.nextFloat())
                recipeInventory.extractItem(i, lastRecipe.getInputCount(i), false);
        });
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

    protected boolean canOutput(SimpleMachineRecipe recipe) {
        if (recipe.getAllInputs() == null)
            return false;

        return IntStream.range(0, recipe.getAllOutputs().length).allMatch(i -> recipeInventory.insertItem(recipeList.getMaxInputs() + i, recipe.getOutput(i).copy(), true).isEmpty());
    }

    protected void output(SimpleMachineRecipe recipe) {
        Random rand = new Random();
        IntStream.range(0, recipe.getAllOutputs().length).forEach(i -> {
            if (recipe.getOutputChance(i) >= rand.nextFloat())
                recipeInventory.insertItem(recipeList.getMaxInputs() + i, recipe.getOutput(i).copy(), true);
        });
    }

    public void dropInventory() {
        StreamUtils.repeat(combinedHandler.getSlots(), i -> ItemUtils.spawnItemInWorld(world, pos, combinedHandler.getStackInSlot(i)));
    }

    protected abstract T getRecipeByInput();
}