package com.emosewapixel.pixellib.fluids

import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.types.FluidType
import net.minecraft.fluid.Fluid
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.util.text.translation.LanguageMap
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.FluidStack

class MaterialFluidAttributes(builder: FluidAttributes.Builder, fluid: Fluid, val nameFun: MaterialFluidAttributes.() -> ITextComponent) : FluidAttributes(builder, fluid) {
    override fun getDisplayName(stack: FluidStack?) = nameFun()

    class Builder(mat: Material, type: FluidType) : FluidAttributes.Builder(ResourceLocation(type.locationBase + "_still"), ResourceLocation(type.locationBase + "_flow"), { builder: FluidAttributes.Builder, fluid: Fluid -> MaterialFluidAttributes(builder, fluid) { if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else type.localize(mat) } }) {
        init {
            color(type.fluidColor(mat))
            overlay(type.overlayTexture)
            temperature(type.temperatureFun(mat))
            sound(type.fillSound, type.emptySound)
            density(type.densityFun(mat))
            luminosity(type.luminosityFun(mat))
            viscosity(type.viscosityFun(mat))
            translationKey(type.buildRegistryName(mat).toString())
            if (type.gaseousFun(mat))
                gaseous()
        }
    }
}