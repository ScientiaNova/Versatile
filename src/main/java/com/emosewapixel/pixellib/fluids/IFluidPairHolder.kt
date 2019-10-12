package com.emosewapixel.pixellib.fluids

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import net.minecraft.fluid.FlowingFluid
import net.minecraft.fluid.Fluid
import org.openzen.zencode.java.ZenCodeType

@ZenRegister
@ZenCodeType.Name("pixellib.fluids.IFluidPairHolder")
interface IFluidPairHolder {
    val still: FlowingFluid
        @ZenCodeType.Getter get
    val flowing: Fluid
        @ZenCodeType.Getter get

    operator fun component1() = still
    operator fun component2() = flowing
}