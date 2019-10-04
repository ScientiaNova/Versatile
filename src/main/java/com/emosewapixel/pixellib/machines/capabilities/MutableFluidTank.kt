package com.emosewapixel.pixellib.machines.capabilities

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.templates.FluidTank

class MutableFluidTank(capacity: Int, validator: (FluidStack) -> Boolean = { true }) : FluidTank(capacity, validator), IMutableFluidTank