package com.scientianovateam.versatile.machines.capabilities.fluids

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.templates.FluidTank

open class MutableFluidTank(capacity: Int, validator: (FluidStack) -> Boolean = { true }) : FluidTank(capacity, validator), IMutableFluidTank, IFluidHandlerModifiable {
    override fun setFluidInTank(tank: Int, stack: FluidStack) = setFluid(stack)
}