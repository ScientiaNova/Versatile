package com.scientianovateam.versatile.machines.capabilities.fluids

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

open class InputFluidStackHandler(count: Int, capacity: Int = 10_000) : FluidStackHandler(count, capacity), IContainerFluidHandler {
    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack = FluidStack.EMPTY

    override fun drain(resource: FluidStack?, action: IFluidHandler.FluidAction): FluidStack = FluidStack.EMPTY

    override fun forceDrain(resource: FluidStack, action: IFluidHandler.FluidAction) = super.drain(resource, action)
}