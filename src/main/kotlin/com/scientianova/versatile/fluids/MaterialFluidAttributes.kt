package com.scientianova.versatile.fluids

import com.scientianova.versatile.common.extensions.plus
import com.scientianova.versatile.materialsystem.elements.ElementUtils
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.main.Form
import net.minecraft.fluid.Fluid
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvents
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.LanguageMap
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.FluidStack

class MaterialFluidAttributes(builder: FluidAttributes.Builder, fluid: Fluid, val nameFun: MaterialFluidAttributes.() -> ITextComponent) : FluidAttributes(builder, fluid) {
    override fun getDisplayName(stack: FluidStack?) = nameFun()

    class Builder(mat: Material, type: Form, baseLocation: ResourceLocation) : FluidAttributes.Builder(baseLocation + "_still", baseLocation + "_flow", { builder: FluidAttributes.Builder, fluid: Fluid -> MaterialFluidAttributes(builder, fluid) { if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else type.localize(mat) } }) {
        init {
            translationKey(type.registryName(mat).toString())
            color(type.color(mat) or (mat.alpha shl 24))
            sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY)
            temperature(type.temperature(mat))
            luminosity(((mat.fluidTemperature - 500) / 50).coerceIn(0, 15))
            viscosity(ElementUtils.getTotalDensity(mat, type).toInt().let { if (type.isGas(mat)) -it else it })
            viscosity(ElementUtils.getTotalDensity(mat, type).toInt())
        }
    }
}