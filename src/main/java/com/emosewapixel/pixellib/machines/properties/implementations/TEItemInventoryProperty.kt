package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.capabilities.ImprovedItemStackHandler
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import net.minecraft.nbt.CompoundNBT

open class TEItemInventoryProperty(value: ImprovedItemStackHandler, override val id: String) : ItemInventoryProperty(value), ITEBoundProperty {
    override fun detectAndSendChanges(container: BaseContainer) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun copy() = TEItemInventoryProperty(handler, id)

    override fun deserializeNBT(nbt: CompoundNBT) {
        handler.deserializeNBT(nbt.getCompound(id))
    }

    override fun serializeNBT() = nbt {
        id to handler
    }
}