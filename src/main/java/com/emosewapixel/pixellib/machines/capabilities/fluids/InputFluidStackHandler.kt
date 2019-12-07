package com.emosewapixel.pixellib.machines.capabilities.fluids

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

open class InputFluidStackHandler(count: Int, capacity: Int) : FluidStackHandler(count, capacity) {
    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack = FluidStack.EMPTY

    override fun drain(resource: FluidStack?, action: IFluidHandler.FluidAction): FluidStack = FluidStack.EMPTY
}