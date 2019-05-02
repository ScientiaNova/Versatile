package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.blocks.BlockSawmill;
import com.EmosewaPixel.pixellib.recipes.MachineRecipe;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileEntitySawmill extends TileEntity implements ITickable {
    private int progress = 0;
    public int slotCount = 2;
    private int curSignal;
    private static int prevSignals[] = new int[8];
    private boolean highForCurrent;

    private ArrayList<MachineRecipe> recipes = RecipeTypes.sawmillRecipes;
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

    public TileEntitySawmill() {
        super(ExpertTypes.SAWMILL);
    }

    private ItemStackHandler input = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            for (MachineRecipe recipe : TileEntitySawmill.this.recipes)
                if (recipe.itemBelongsInRecipe(stack))
                    return true;

            return false;
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileEntitySawmill.this.currentRecipe = getRecipeByInput();
            TileEntitySawmill.this.markDirty();
        }
    };

    private ItemStackHandler output = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return false;
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileEntitySawmill.this.markDirty();
        }
    };

    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(input, output);

    @Override
    public void tick() {
        if (!world.isRemote) {
            curSignal = world.getRedstonePowerFromNeighbors(pos);
            if (progress > 0) {
                if (shouldContinueProcess()) {
                    int highestPrev = getHighestPrev();
                    if (highForCurrent == (highestPrev > 8) && highestPrev != 0) {
                        for (int prevSignal : prevSignals)
                            if ((prevSignal == 0) != (curSignal == 0)) {
                                world.setBlockState(pos, world.getBlockState(pos).with(BlockSawmill.ON, true));
                                progress--;
                                break;
                            } else
                                world.setBlockState(pos, world.getBlockState(pos).with(BlockSawmill.ON, false));
                        if (progress == 0)
                            cut();
                    } else
                        progress = 0;
                } else
                    progress = 0;
            } else
                startCutting();
            if (progress == 0)
                world.setBlockState(pos, world.getBlockState(pos).with(BlockSawmill.ON, false));
            pushPrevs();
            markDirty();
        }
    }

    protected void startCutting() {
        if (!currentRecipe.isEmpty()) {
            if (highForCurrent != (getHighestPrev() > 8))
                currentRecipe = getRecipeByInput();

            if (canOutput(currentRecipe, true))
                progress = currentRecipe.getTime() - 1;
            else
                world.setBlockState(pos, world.getBlockState(pos).with(BlockSawmill.ON, false));
        }
    }

    protected void cut() {
        if (!currentRecipe.isEmpty())
            if (canOutput(currentRecipe, false))
                input.extractItem(0, ((ItemStack) currentRecipe.getInput(0)).copy().getCount(), false);
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        NBTTagList prevList = compound.getList("Prev", Constants.NBT.TAG_INT);
        for (int i = 0; i < prevList.size(); i++)
            prevSignals[i] = prevList.getCompound(i).getInt("Prev");
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
        NBTTagList prevs = new NBTTagList();
        for (int prev : prevSignals) {
            NBTTagCompound aPrev = new NBTTagCompound();
            aPrev.setInt("Prev", prev);
            prevs.add(aPrev);
        }
        compound.setTag("PrevSignals", prevs);
        compound.setTag("InputItems", input.serializeNBT());
        compound.setTag("OutputItems", output.serializeNBT());
        compound.setInt("Progress", progress);
        return compound;
    }

    protected boolean shouldContinueProcess() {
        if (currentRecipe.isEmpty())
            return false;

        if (!input.getStackInSlot(0).isEmpty())
            return (input.getStackInSlot(0).getItem() == ((ItemStack) currentRecipe.getInput(0)).getItem() && input.getStackInSlot(0).getCount() >= ((ItemStack) currentRecipe.getInput(0)).getCount());
        return false;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    private void pushPrevs() {
        for (int i = prevSignals.length - 1; i > 0; i--)
            prevSignals[i] = prevSignals[i - 1];

        prevSignals[0] = curSignal;
    }

    private int getHighestPrev() {
        int highestSignal = curSignal;
        for (int prevSignal : prevSignals)
            if (prevSignal > highestSignal)
                highestSignal = prevSignal;

        return highestSignal;
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

    private MachineRecipe getRecipeByInput() {
        if (input.getStackInSlot(0).isEmpty())
            return MachineRecipe.EMPTY;

        MachineRecipe returnRecipe;
        for (MachineRecipe recipe : recipes)
            if (recipe.isInputValid(new ItemStack[]{input.getStackInSlot(0)}) && ((RecipeTypes.SawmillRecipe) recipe).isHighSignal() == (getHighestPrev() > 8)) {
                highForCurrent = ((RecipeTypes.SawmillRecipe) recipe).isHighSignal();
                returnRecipe = new MachineRecipe(new ItemStack[]{new ItemStack(input.getStackInSlot(0).getItem(), recipe.getCountOfInputItem(input.getStackInSlot(0)))}, recipe.getAllOutputs(), recipe.getTime());
                return returnRecipe;
            }
        return MachineRecipe.EMPTY;
    }

    protected boolean canOutput(MachineRecipe recipe, boolean simulate) {
        return output.insertItem(0, recipe.getOutput(0).copy(), simulate).isEmpty();
    }

    public void dropInventory() {
        for (int i = 0; i < slotCount; i++) {
            ItemStack stack = combinedHandler.getStackInSlot(i);

            world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack));
        }
    }
}
