package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.blocks.BlockFurnaceBase;
import com.EmosewaPixel.pixellib.recipes.MachineRecipe;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileEntityFurnaceBase extends TileEntity implements ITickable {
    private int progress = 0;
    private int burnTime = 0;
    private int maxBurnTime = 0;
    private int inputCount;
    private int outputCount;
    public int slotCount;

    private ArrayList<MachineRecipe> recipes;
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

    public void setBurnTime(int i) {
        burnTime = i;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public void setMaxBurnTime(int i) {
        maxBurnTime = i;
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }

    public int getInputCount() {
        return inputCount;
    }

    public int getOutputCount() {
        return outputCount;
    }

    public TileEntityFurnaceBase(TileEntityType type, int inputCount, int outputCount, ArrayList<MachineRecipe> recipes) {
        super(type);
        this.inputCount = inputCount;
        this.outputCount = outputCount;
        slotCount = inputCount + 1 + outputCount;
        this.recipes = recipes;

        input = new ItemStackHandler(inputCount) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                for (MachineRecipe recipe : TileEntityFurnaceBase.this.recipes)
                    if (recipe.itemBelongsInRecipe(stack))
                        return true;

                return false;
            }

            @Override
            protected void onContentsChanged(int slot) {
                TileEntityFurnaceBase.this.currentRecipe = getRecipeByInput();
                TileEntityFurnaceBase.this.markDirty();
            }
        };

        fuel_input = new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return getItemBurnTime(stack) > 0;
            }

            @Override
            protected void onContentsChanged(int slot) {
                TileEntityFurnaceBase.this.markDirty();
            }
        };

        output = new ItemStackHandler(outputCount) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return false;
            }

            @Override
            protected void onContentsChanged(int slot) {
                TileEntityFurnaceBase.this.markDirty();
            }
        };

        combinedHandler = new CombinedInvWrapper(input, fuel_input, output);
    }

    protected ItemStackHandler input;

    protected ItemStackHandler fuel_input;

    protected ItemStackHandler output;

    protected CombinedInvWrapper combinedHandler;

    @Override
    public void tick() {
        if (!world.isRemote) {
            if (burnTime > 0) {
                burnTime--;
                world.setBlockState(pos, world.getBlockState(pos).with(BlockFurnaceBase.LIT, true));
                if (progress > 0) {
                    if (shouldContinueProcess()) {
                        progress--;
                        if (progress == 0)
                            smelt();
                    } else
                        progress = 0;
                } else
                    startSmelting();
            } else {
                if (!fuel_input.getStackInSlot(0).isEmpty())
                    consumeFuel();
                else
                    world.setBlockState(pos, world.getBlockState(pos).with(BlockFurnaceBase.LIT, false));
            }
            markDirty();
        }
    }

    protected int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty())
            return 0;
        int rec = stack.getBurnTime();
        return ForgeEventFactory.getItemBurnTime(stack, rec == -1 ? TileEntityFurnace.getBurnTimes().getOrDefault(stack.getItem(), 0) : rec);
    }

    protected void startSmelting() {
        if (!currentRecipe.isEmpty())
            if (canOutput(currentRecipe, true))
                progress = currentRecipe.getTime() - 1;
    }

    protected void smelt() {
        MachineRecipe lastRecipe = currentRecipe;
        canOutput(lastRecipe, false);
        for (int i = 0; i < inputCount; i++)
            input.extractItem(i, lastRecipe.getInputCount(i), false);
    }

    protected void consumeFuel() {
        if (!currentRecipe.isEmpty() && canOutput(currentRecipe, true)) {
            burnTime = maxBurnTime = getItemBurnTime(fuel_input.getStackInSlot(0));
            if (burnTime > 0) {
                if (fuel_input.getStackInSlot(0).getItem().hasContainerItem())
                    fuel_input.setStackInSlot(0, new ItemStack(fuel_input.getStackInSlot(0).getItem().getContainerItem()));
                else
                    fuel_input.extractItem(0, 1, false);
            }
        } else
            world.setBlockState(pos, world.getBlockState(pos).with(BlockFurnaceBase.LIT, false));
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        if (compound.hasKey("InputItems"))
            input.deserializeNBT((NBTTagCompound) compound.getTag("InputItems"));
        if (compound.hasKey("FuelItems"))
            fuel_input.deserializeNBT((NBTTagCompound) compound.getTag("FuelItems"));
        if (compound.hasKey("OutputItems"))
            output.deserializeNBT((NBTTagCompound) compound.getTag("OutputItems"));
        currentRecipe = getRecipeByInput();
        progress = compound.getInt("Progress");
        burnTime = compound.getInt("BurnTime");
        maxBurnTime = compound.getInt("MaxBurnTime");
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        compound.setTag("InputItems", input.serializeNBT());
        compound.setTag("FuelItems", fuel_input.serializeNBT());
        compound.setTag("OutputItems", output.serializeNBT());
        compound.setInt("Progress", progress);
        compound.setInt("BurnTime", burnTime);
        compound.setInt("MaxBurnTime", maxBurnTime);
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
            if (side == EnumFacing.EAST || side == EnumFacing.WEST || side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
                return LazyOptional.of(() -> this.fuel_input).cast();

            if (side == EnumFacing.DOWN)
                return LazyOptional.of(() -> this.output).cast();
        }
        return LazyOptional.empty();
    }

    protected boolean shouldContinueProcess() {
        if (currentRecipe.isEmpty())
            return false;

        int matches = 0;
        for (int i = 0; i < inputCount; i++)
            if (!input.getStackInSlot(i).isEmpty())
                if (input.getStackInSlot(i).getItem() == ((ItemStack) currentRecipe.getInput(i)).getItem() && input.getStackInSlot(i).getCount() >= currentRecipe.getInputCount(i))
                    matches++;
        return matches == inputCount;
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
