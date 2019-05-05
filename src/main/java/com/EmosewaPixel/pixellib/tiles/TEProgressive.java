package com.EmosewaPixel.pixellib.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TEProgressive extends TileEntity implements ITickable {
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

    public TEProgressive(TileEntityType type) {
        super(type);
    }

    @Override
    public void tick() {
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        progress = compound.getInt("Progress");
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        compound.setInt("Progress", progress);
        return compound;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
        return LazyOptional.empty();
    }
}
