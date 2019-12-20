package com.emosewapixel.pixellib.machines.capabilities.fluids

import net.minecraftforge.fluids.FluidStack

open class NonRepeatingInputFluidStackHandler(count: Int, capacity: Int = 10_000) : InputFluidStackHandler(count, capacity) {
    override fun isFluidValid(tank: Int, stack: FluidStack) = !tanks.withIndex().any {
        it.index != tank && it.value.isFluidEqual(stack)
    } && super.isFluidValid(tank, stack)
}