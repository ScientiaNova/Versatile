package com.emosewapixel.pixellib.fluids

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import net.minecraft.fluid.FlowingFluid
import net.minecraft.fluid.Fluid
import org.openzen.zencode.java.ZenCodeType

@ZenRegister
@ZenCodeType.Name("pixellib.fluids.FluidPairHolder")
class FluidPairHolder @ZenCodeType.Constructor constructor(override val still: FlowingFluid, override val flowing: Fluid) : IFluidPairHolder