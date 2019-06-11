package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.blocks.FuelBasedMachineBlock;
import com.EmosewaPixel.pixellib.recipes.SimpleRecipeList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FuelBasedTE extends RecipeBasedTE {
    private int burnTime = 0;
    private int maxBurnTime = 0;

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

    public FuelBasedTE(TileEntityType type, SimpleRecipeList list) {
        super(type, list);

        fuel_input = new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return getItemBurnTime(stack) > 0;
            }

            @Override
            protected void onContentsChanged(int slot) {
                FuelBasedTE.this.markDirty();
            }
        };

        combinedHandler = new CombinedInvWrapper(recipeInventory, fuel_input);
    }

    public ItemStackHandler fuel_input;

    @Override
    public void tick() {
        if (!world.isRemote) {
            if (burnTime > 0) {
                burnTime--;
                world.setBlockState(pos, world.getBlockState(pos).with(FuelBasedMachineBlock.LIT, true));
                super.tick();
            } else {
                if (!fuel_input.getStackInSlot(0).isEmpty())
                    consumeFuel();
                else
                    world.setBlockState(pos, world.getBlockState(pos).with(FuelBasedMachineBlock.LIT, false));
            }
            markDirty();
        }
    }

    protected int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty())
            return 0;
        int rec = stack.getBurnTime();
        return ForgeEventFactory.getItemBurnTime(stack, rec == -1 ? FurnaceTileEntity.getBurnTimes().getOrDefault(stack.getItem(), 0) : rec);
    }

    protected void consumeFuel() {
        if (!getCurrentRecipe().isEmpty() && canOutput(getCurrentRecipe(), true)) {
            burnTime = maxBurnTime = getItemBurnTime(fuel_input.getStackInSlot(0));
            if (burnTime > 0) {
                if (fuel_input.getStackInSlot(0).hasContainerItem())
                    fuel_input.setStackInSlot(0, fuel_input.getStackInSlot(0).getContainerItem());
                else
                    fuel_input.extractItem(0, 1, false);
            }
        } else
            world.setBlockState(pos, world.getBlockState(pos).with(FuelBasedMachineBlock.LIT, false));
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("FuelItems"))
            fuel_input.deserializeNBT((CompoundNBT) compound.get("FuelItems"));
        burnTime = compound.getInt("BurnTime");
        maxBurnTime = compound.getInt("MaxBurnTime");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("FuelItems", fuel_input.serializeNBT());
        compound.putInt("BurnTime", burnTime);
        compound.putInt("MaxBurnTime", maxBurnTime);
        return compound;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (side == Direction.EAST || side == Direction.WEST || side == Direction.NORTH || side == Direction.SOUTH)
                return LazyOptional.of(() -> this.fuel_input).cast();
        return super.getCapability(cap, side);
    }
}
