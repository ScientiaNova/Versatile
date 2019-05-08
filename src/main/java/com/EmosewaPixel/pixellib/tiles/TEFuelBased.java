package com.EmosewaPixel.pixellib.tiles;

import com.EmosewaPixel.pixellib.blocks.BlockMachineFuelBased;
import com.EmosewaPixel.pixellib.recipes.SimpleRecipeList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TEFuelBased extends TERecipeBased {
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

    public TEFuelBased(TileEntityType type, SimpleRecipeList list) {
        super(type, list);
        setSlotCount(list.getMaxInputs() + 1 + list.getMaxOutputs());

        fuel_input = new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return getItemBurnTime(stack) > 0;
            }

            @Override
            protected void onContentsChanged(int slot) {
                TEFuelBased.this.markDirty();
            }
        };

        combinedHandler = new CombinedInvWrapper(input, fuel_input, output);
    }

    public ItemStackHandler fuel_input;

    @Override
    public void tick() {
        if (!world.isRemote) {
            if (burnTime > 0) {
                burnTime--;
                world.setBlockState(pos, world.getBlockState(pos).with(BlockMachineFuelBased.LIT, true));
                super.tick();
            } else {
                if (!fuel_input.getStackInSlot(0).isEmpty())
                    consumeFuel();
                else
                    world.setBlockState(pos, world.getBlockState(pos).with(BlockMachineFuelBased.LIT, false));
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

    protected void consumeFuel() {
        if (!getCurrentRecipe().isEmpty() && canOutput(getCurrentRecipe(), true)) {
            burnTime = maxBurnTime = getItemBurnTime(fuel_input.getStackInSlot(0));
            if (burnTime > 0) {
                if (fuel_input.getStackInSlot(0).getItem().hasContainerItem())
                    fuel_input.setStackInSlot(0, new ItemStack(fuel_input.getStackInSlot(0).getItem().getContainerItem()));
                else
                    fuel_input.extractItem(0, 1, false);
            }
        } else
            world.setBlockState(pos, world.getBlockState(pos).with(BlockMachineFuelBased.LIT, false));
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        if (compound.contains("FuelItems"))
            fuel_input.deserializeNBT((NBTTagCompound) compound.get("FuelItems"));
        burnTime = compound.getInt("BurnTime");
        maxBurnTime = compound.getInt("MaxBurnTime");
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        compound.put("FuelItems", fuel_input.serializeNBT());
        compound.putInt("BurnTime", burnTime);
        compound.putInt("MaxBurnTime", maxBurnTime);
        return compound;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (side == EnumFacing.EAST || side == EnumFacing.WEST || side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
                return LazyOptional.of(() -> this.fuel_input).cast();
        return super.getCapability(cap, side);
    }
}
