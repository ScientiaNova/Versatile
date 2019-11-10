package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.capabilities.ImprovedItemStackHandler
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.properties.IItemHandlerProperty
import net.minecraft.nbt.CompoundNBT

open class ItemInventoryProperty(override val handler: ImprovedItemStackHandler) : IItemHandlerProperty {
    override fun detectAndSendChanges(container: BaseContainer) {}

    override fun copy() = ItemInventoryProperty(handler)

    override fun deserializeNBT(nbt: CompoundNBT) {}

    override fun serializeNBT() = nbt { }
}