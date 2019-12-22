package com.scientianovateam.versatile.machines.capabilities.fluids

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

interface IFluidHandlerModifiable : IFluidHandler {
    fun setFluidInTank(tank: Int, stack: FluidStack)
}