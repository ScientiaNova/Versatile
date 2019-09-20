package com.emosewapixel.pixellib.machines

import com.emosewapixel.pixellib.extensions.plusAssign
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional


class BaseTileEntity(type: TileEntityType<*>) : TileEntity(type), ITickableTileEntity {
    val block
        get() = blockState.block as? IMachineBlock

    val properties = block?.properties?.mapValues { (_, value) -> value() }?.toMutableMap() ?: mutableMapOf()

    override fun tick() {
        block?.tick?.invoke(this)
    }

    override fun serializeNBT(): CompoundNBT {
        val nbt = super.serializeNBT()
        val b = block?.serializeNBT?.invoke(this)
        if (b != null)
            nbt += b
        return nbt
    }

    override fun deserializeNBT(nbt: CompoundNBT) {
        super.deserializeNBT(nbt)
        block?.deserializeNBT?.invoke(this, nbt)
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?) =
            properties.values.asSequence().filterIsInstance<ICapabilitySerializable<*>>().map { it.getCapability(cap, side) }.firstOrNull { it.isPresent }
                    ?: LazyOptional.empty()

    fun canInteractWith(playerIn: PlayerEntity) = playerIn.getDistanceSq(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5) <= 64
}