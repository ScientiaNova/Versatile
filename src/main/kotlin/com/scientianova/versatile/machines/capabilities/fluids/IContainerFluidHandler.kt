package com.scientianova.versatile.machines.capabilities.fluids

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction

interface IContainerFluidHandler : IFluidHandler {
    fun forceFill(resource: FluidStack, action: FluidAction) = fill(resource, action)

    fun forceDrain(resource: FluidStack, action: FluidAction) = drain(resource, action)
}