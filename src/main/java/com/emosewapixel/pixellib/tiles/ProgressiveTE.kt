package com.emosewapixel.pixellib.tiles

import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional

open class ProgressiveTE(type: TileEntityType<*>) : TileEntity(type), ITickableTileEntity {
    var progress = 0

    override fun tick() {}

    override fun read(compound: CompoundNBT) {
        super.read(compound)
        progress = compound.getInt("Progress")
    }

    override fun write(compound: CompoundNBT): CompoundNBT {
        super.write(compound)
        compound.putInt("Progress", progress)
        return compound
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> = LazyOptional.empty()
}