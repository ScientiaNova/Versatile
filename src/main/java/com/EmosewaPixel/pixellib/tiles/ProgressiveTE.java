package com.EmosewaPixel.pixellib.tiles;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ProgressiveTE extends TileEntity implements ITickableTileEntity {
    private int progress = 0;

    public void setProgress(int i) {
        progress = i;
    }

    public int getProgress() {
        return progress;
    }

    public int subtractProgress(int amount) {
        return progress -= amount;
    }

    public ProgressiveTE(TileEntityType type) {
        super(type);
    }

    @Override
    public void tick() {
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        progress = compound.getInt("Progress");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("Progress", progress);
        return compound;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return LazyOptional.empty();
    }
}