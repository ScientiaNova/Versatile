package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.capabilities.FluidStackHandler
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import net.minecraft.nbt.CompoundNBT

open class TEFluidInventoryProperty(value: FluidStackHandler, override val id: String) : FluidInventoryProperty(value), ITEBoundProperty {
    override fun detectAndSendChanges(container: BaseContainer) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun copy() = TEFluidInventoryProperty(handler, id)

    override fun deserializeNBT(nbt: CompoundNBT) {
        handler.deserializeNBT(nbt.getCompound(id))
    }

    override fun serializeNBT() = nbt {
        id to handler
    }
}