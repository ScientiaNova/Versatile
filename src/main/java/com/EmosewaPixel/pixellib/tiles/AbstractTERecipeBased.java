package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.recipes.AbstractRecipeList;
import com.EmosewaPixel.pixellib.recipes.SimpleMachineRecipe;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractTERecipeBased<T extends SimpleMachineRecipe> extends TEProgressive {
    private int slotCount;
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
        return slotCount;
    }

    protected void setSlotCount(int count) {
        slotCount = count;
    }

    public AbstractTERecipeBased(TileEntityType type, AbstractRecipeList<T, ?> recipeList) {
        super(type);
        slotCount = recipeList.getMaxInputs() + recipeList.getMaxOutputs();
        this.recipeList = recipeList;

        input = new ItemStackHandler(recipeList.getMaxInputs()) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                for (SimpleMachineRecipe recipe : recipeList.getReipes())
                    if (recipe.itemBelongsInRecipe(stack))
                        return true;

                return false;
            }

            @Override
            protected void onContentsChanged(int slot) {
                AbstractTERecipeBased.this.currentRecipe = getRecipeByInput();
                AbstractTERecipeBased.this.markDirty();
            }
        };

        output = new ItemStackHandler(recipeList.getMaxOutputs()) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return false;
            }

            @Override
            protected void onContentsChanged(int slot) {
                AbstractTERecipeBased.this.markDirty();
            }
        };

        combinedHandler = new CombinedInvWrapper(input, output);
    }

    protected static ItemStackHandler input;

    protected static ItemStackHandler output;

    protected static CombinedInvWrapper combinedHandler;

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
            input.extractItem(i, lastRecipe.getInputCount(i), false);
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        if (compound.hasKey("InputItems"))
            input.deserializeNBT((NBTTagCompound) compound.getTag("InputItems"));
        if (compound.hasKey("OutputItems"))
            output.deserializeNBT((NBTTagCompound) compound.getTag("OutputItems"));
        currentRecipe = getRecipeByInput();
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        compound.setTag("InputItems", input.serializeNBT());
        compound.setTag("OutputItems", output.serializeNBT());
        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null)
                return LazyOptional.of(() -> combinedHandler).cast();
            if (side == EnumFacing.UP)
                return LazyOptional.of(() -> this.input).cast();
            if (side == EnumFacing.DOWN)
                return LazyOptional.of(() -> this.output).cast();
        }
        return LazyOptional.empty();
    }

    protected boolean canOutput(SimpleMachineRecipe recipe, boolean simulate) {
        boolean can = true;

        for (int i = 0; i < recipe.getAllOutputs().length; i++)
            if (!output.insertItem(i, recipe.getOutput(i).copy(), simulate).isEmpty())
                can = false;

        return can;
    }

    public void dropInventory() {
        for (int i = 0; i < slotCount; i++) {
            ItemStack stack = combinedHandler.getStackInSlot(i);

            world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack));
        }
    }

    abstract T getRecipeByInput();
}
