package com.emosewapixel.pixellib.machines.capabilities

import net.minecraftforge.fluids.FluidStack

interface IMutableFluidHandler {
    fun setFluidInTank(tank: Int, stack: FluidStack)
}