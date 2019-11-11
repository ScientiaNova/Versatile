package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.machines.capabilities.FluidStackHandler
import com.emosewapixel.pixellib.machines.capabilities.IFluidHandlerModifiable
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.fluids.capability.CapabilityFluidHandler

open class FluidInventoryProperty(override val value: FluidStackHandler) : IValueProperty<IFluidHandlerModifiable> {
    override fun copy() = FluidInventoryProperty(value)

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> =
            if (cap === CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
                LazyOptional.of(::value).cast()
            else LazyOptional.empty()
}