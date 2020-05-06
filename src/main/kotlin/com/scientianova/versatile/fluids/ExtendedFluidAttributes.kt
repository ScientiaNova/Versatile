package com.scientianova.versatile.fluids

import net.minecraft.fluid.Fluid
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.FluidStack

class ExtendedFluidAttributes(builder: ExtendedFluidAttributes.Builder, fluid: Fluid) : FluidAttributes(builder, fluid) {
    private val localizedNameFun = builder.localizedNameFun

    override fun getDisplayName(stack: FluidStack?) = localizedNameFun()

    class Builder(
            stillTexture: ResourceLocation,
            flowingTexture: ResourceLocation,
            overlay: ResourceLocation? = null,
            color: Int = -1,
            isGas: Boolean = false,
            fillSound: SoundEvent = SoundEvents.ITEM_BUCKET_FILL,
            emptySound: SoundEvent = SoundEvents.ITEM_BUCKET_EMPTY,
            temperature: Int = 300,
            luminosity: Int = 0,
            viscosity: Int = 0,
            density: Int = 0,
            val localizedNameFun: ExtendedFluidAttributes.() -> ITextComponent = { TranslationTextComponent(translationKey) }
    ) : FluidAttributes.Builder(stillTexture, flowingTexture, { builder, fluid -> ExtendedFluidAttributes(builder as Builder, fluid) }) {
        init {
            color(color)
            if (isGas) gaseous()
            overlay?.let(this::overlay)
            sound(fillSound, emptySound)
            temperature(temperature)
            luminosity(luminosity)
            viscosity(viscosity)
            density(density)
        }
    }
}