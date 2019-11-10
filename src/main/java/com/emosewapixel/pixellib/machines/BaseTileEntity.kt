package com.emosewapixel.pixellib.machines

import com.emosewapixel.pixellib.extensions.plusAssign
import com.emosewapixel.pixellib.machines.gui.layout.GUIBook
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional


class BaseTileEntity(type: TileEntityType<*>) : TileEntity(type), ITickableTileEntity {
    val block
        get() = blockState.block as? IMachineBlock

    val guiLayout = block?.guiLayout?.invoke(this) ?: GUIBook()

    val properties = block?.properties?.invoke(this)?.map { it.id to it }?.toMap() ?: mapOf()

    override fun tick() {
        block?.tick?.invoke(this)
    }

    fun update() {
        block?.update?.invoke(this)
    }

    override fun serializeNBT(): CompoundNBT {
        val nbt = super.serializeNBT()
        properties.values.forEach { nbt += it.serializeNBT() }
        return nbt
    }

    override fun deserializeNBT(nbt: CompoundNBT) {
        super.deserializeNBT(nbt)
        properties.values.forEach { it.deserializeNBT(nbt) }
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?) =
            properties.values.asSequence().map { it.getCapability(cap, side) }.firstOrNull(LazyOptional<*>::isPresent)
                    ?: LazyOptional.empty()

    fun canInteractWith(playerIn: PlayerEntity) = playerIn.getDistanceSq(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5) <= 64
}