package com.emosewapixel.pixellib.machines.capabilities.fluids

import net.minecraftforge.fluids.FluidStack

open class OutputFluidStackHandler(count: Int, capacity: Int) : FluidStackHandler(count, capacity) {
    override fun isFluidValid(tank: Int, stack: FluidStack) = false
}