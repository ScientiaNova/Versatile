package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.recipes.MachineRecipe;
import net.minecraft.block.BlockBookshelf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileEntityInfusionTable extends TileEntity implements ITickable {
    private int progress = 0;
    private int inputCount = 2;
    private int outputCount = 1;
    public int slotCount = inputCount + outputCount;

    private ArrayList<MachineRecipe> recipes = RecipeTypes.infusionRecipes;
    private MachineRecipe currentRecipe = MachineRecipe.EMPTY;

    public void setProgress(int i) {
        progress = i;
    }

    public int getProgress() {
        return progress;
    }

    public MachineRecipe getCurrentRecipe() {
        return currentRecipe;
    }

    public void setCurrentRecipe(MachineRecipe recipe) {
        currentRecipe = recipe;
    }

    public TileEntityInfusionTable() {
        super(ExpertTypes.INFUSION_TABLE);
    }

    private ItemStackHandler input = new ItemStackHandler(2) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            for (MachineRecipe recipe : TileEntityInfusionTable.this.recipes)
                if (recipe.itemBelongsInRecipe(stack))
                    return true;

            return false;
        }

        @Override
        protected void onContentsChanged(int slot) {
            currentRecipe = getRecipeByInput();
            TileEntityInfusionTable.this.markDirty();
        }
    };

    private ItemStackHandler output = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return false;
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileEntityInfusionTable.this.markDirty();
        }
    };

    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(input, output);

    @Override
    public void tick() {
        if (!world.isRemote) {
            if (progress > 0) {
                if (shouldContinueProcess()) {
                    progress -= countShelves();
                    if (progress <= 0)
                        infuse();
                } else
                    progress = 0;
            } else
                startInfusing();
            markDirty();
        }
    }

    private void startInfusing() {
        if (!currentRecipe.isEmpty())
            if (canOutput(currentRecipe, true)) {
                progress = currentRecipe.getTime();
            }
    }

    private void infuse() {
        MachineRecipe lastRecipe = currentRecipe;
        canOutput(lastRecipe, false);
        for (int i = 0; i < inputCount; i++)
            input.extractItem(i, lastRecipe.getInputCount(i), false);
        progress = 0;
    }

    private int countShelves() {
        int shelves = 0;
        for (int i = -2; i < 3; i++)
            for (int j = -2; j < 3; j++)
                for (int h = 0; h < 2; h++) {
                    if (world.getBlockState(new BlockPos(pos.getX() + i, pos.getY() + h, pos.getZ() + j)).getBlock() instanceof BlockBookshelf)
                        shelves++;
                }
        return shelves;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        if (compound.hasKey("InputItems"))
            input.deserializeNBT((NBTTagCompound) compound.getTag("InputItems"));
        if (compound.hasKey("OutputItems"))
            output.deserializeNBT((NBTTagCompound) compound.getTag("OutputItems"));
        currentRecipe = getRecipeByInput();
        progress = compound.getInt("Progress");
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        compound.setTag("InputItems", input.serializeNBT());
        compound.setTag("OutputItems", output.serializeNBT());
        compound.setInt("Progress", progress);
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

    private boolean shouldContinueProcess() {
        if (currentRecipe.isEmpty())
            return false;

        int matches = 0;
        for (int i = 0; i < inputCount; i++)
            if (!input.getStackInSlot(i).isEmpty())
                if (input.getStackInSlot(i).getItem() == ((ItemStack) currentRecipe.getInput(i)).getItem() && input.getStackInSlot(i).getCount() >= currentRecipe.getInputCount(i))
                    matches++;
        return matches == inputCount;
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

    private boolean canOutput(MachineRecipe recipe, boolean simulate) {
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
}
