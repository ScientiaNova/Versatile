package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.capabilities.FluidStackHandler
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.properties.IFluidHandlerProperty
import net.minecraft.nbt.CompoundNBT

open class FluidInventoryProperty(override val handler: FluidStackHandler) : IFluidHandlerProperty {
    override fun detectAndSendChanges(container: BaseContainer) {}

    override fun deserializeNBT(nbt: CompoundNBT) {}

    override fun serializeNBT() = nbt { }
}