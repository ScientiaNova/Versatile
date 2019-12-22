package com.emosewapixel.pixellib.fluids

import net.minecraft.fluid.FlowingFluid
import net.minecraft.fluid.Fluid

interface IFluidPairHolder {
    val still: FlowingFluid
    val flowing: Fluid

    operator fun component1() = still
    operator fun component2() = flowing
}