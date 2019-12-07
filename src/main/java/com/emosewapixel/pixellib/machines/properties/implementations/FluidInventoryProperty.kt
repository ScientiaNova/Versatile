package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.machines.capabilities.FluidStackHandler
import com.emosewapixel.pixellib.machines.capabilities.IFluidHandlerModifiable
import com.emosewapixel.pixellib.machines.properties.IValueProperty

open class FluidInventoryProperty(override val value: FluidStackHandler) : IValueProperty<IFluidHandlerModifiable> {
    override fun createDefault() = FluidInventoryProperty(FluidStackHandler(value.count, value.noOutputTanks, value.noInputTanks, value.capacity))
}