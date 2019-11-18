package com.emosewapixel.pixellib.machines

import com.emosewapixel.pixellib.extensions.plusAssign
import com.emosewapixel.pixellib.machines.gui.layout.GUIBook
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import net.minecraft.block.Block
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional

open class BaseTileEntity(type: TileEntityType<*> = BaseMachineRegistry.BASE_TILE_ENTITY) : TileEntity(type), ITickableTileEntity {
    private var unreadTag: CompoundNBT? = null

    protected val block get() = blockState.block as? IMachineBlock

    val teProperties by lazy {
        block?.teProperties?.invoke(this)?.map { it.id to it }?.toMap().also { map -> unreadTag?.let { tag -> map?.values?.forEach { it.deserializeNBT(tag) } } }
                ?: mapOf()
    }

    val guiLayout by lazy { block?.guiLayout?.invoke(this) ?: GUIBook() }

    override fun tick() = teProperties.values.forEach(ITEBoundProperty::tick)

    fun update() = teProperties.values.forEach(ITEBoundProperty::update)

    override fun write(nbt: CompoundNBT): CompoundNBT {
        teProperties.values.forEach { nbt += it.serializeNBT() }
        return super.write(nbt)
    }

    override fun read(nbt: CompoundNBT) {
        super.read(nbt)
        if (world == null) unreadTag = nbt
        else readByProperties(nbt)
    }

    open fun readByProperties(nbt: CompoundNBT) {
        teProperties.values.forEach { it.deserializeNBT(nbt) }
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?) =
            teProperties.values.asSequence().map { it.getCapability(cap, side) }.firstOrNull(LazyOptional<*>::isPresent)
                    ?: LazyOptional.empty()

    fun canInteractWith(playerIn: PlayerEntity) = playerIn.getDistanceSq(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5) <= 64

    companion object {
        @JvmField
        val USED_BY = mutableListOf<Block>()
    }
}