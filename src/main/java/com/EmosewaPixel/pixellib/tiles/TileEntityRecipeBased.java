package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.recipes.MachineRecipe;
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
import java.util.ArrayList;

public class TileEntityRecipeBased extends TileEntityProgressive implements ITileEntityRecipeBased {
    private int inputCount;
    private int outputCount;
    public int slotCount;

    private ArrayList<MachineRecipe> recipes;
    private MachineRecipe currentRecipe = MachineRecipe.EMPTY;

    public MachineRecipe getCurrentRecipe() {
        return currentRecipe;
    }

    public void setCurrentRecipe(MachineRecipe recipe) {
        currentRecipe = recipe;
    }

    public int getInputCount() {
        return inputCount;
    }

    public int getOutputCount() {
        return outputCount;
    }

    public TileEntityRecipeBased(TileEntityType type, int inputCount, int outputCount, ArrayList<MachineRecipe> recipes) {
        super(type);
        this.inputCount = inputCount;
        this.outputCount = outputCount;
        slotCount = inputCount + 1 + outputCount;
        this.recipes = recipes;

        input = new ItemStackHandler(inputCount) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                for (MachineRecipe recipe : TileEntityRecipeBased.this.recipes)
                    if (recipe.itemBelongsInRecipe(stack))
                        return true;

                return false;
            }

            @Override
            protected void onContentsChanged(int slot) {
                TileEntityRecipeBased.this.currentRecipe = getRecipeByInput();
                TileEntityRecipeBased.this.markDirty();
            }
        };

        output = new ItemStackHandler(outputCount) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return false;
            }

            @Override
            protected void onContentsChanged(int slot) {
                TileEntityRecipeBased.this.markDirty();
            }
        };

        combinedHandler = new CombinedInvWrapper(input, output);
    }

    public static ItemStackHandler input;

    public static ItemStackHandler output;

    public static CombinedInvWrapper combinedHandler;

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

    @Override
    public void startWorking() {
        if (!currentRecipe.isEmpty())
            if (canOutput(currentRecipe, true))
                setProgress(currentRecipe.getTime() - 1);
    }

    @Override
    public void work() {
        MachineRecipe lastRecipe = currentRecipe;
        canOutput(lastRecipe, false);
        for (int i = 0; i < inputCount; i++)
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

    protected boolean canOutput(MachineRecipe recipe, boolean simulate) {
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

    private MachineRecipe getRecipeByInput() {
        ItemStack[] stacks = new ItemStack[input.getSlots()];
        for (int i = 0; i < input.getSlots(); i++) {
            if (input.getStackInSlot(i).isEmpty())
                return MachineRecipe.EMPTY;
            stacks[i] = input.getStackInSlot(i);
        }

        ItemStack recipeInputs[] = new ItemStack[input.getSlots()];
        MachineRecipe returnRecipe;
        for (MachineRecipe recipe : recipes)
            if (recipe.isInputValid(stacks)) {
                for (int i = 0; i < input.getSlots(); i++)
                    recipeInputs[i] = new ItemStack(input.getStackInSlot(i).getItem(), recipe.getCountOfInputItem(input.getStackInSlot(i)));
                returnRecipe = new MachineRecipe(recipeInputs, recipe.getAllOutputs(), recipe.getTime());
                return returnRecipe;
            }
        return MachineRecipe.EMPTY;
    }
}
