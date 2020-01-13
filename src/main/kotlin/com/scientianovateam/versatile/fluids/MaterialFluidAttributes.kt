package com.scientianovateam.versatile.fluids

import com.scientianovateam.versatile.common.extensions.plus
import com.scientianovateam.versatile.materialsystem.elements.ElementUtils
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.Form
import net.minecraft.fluid.Fluid
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvents
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.util.text.LanguageMap
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.FluidStack

class MaterialFluidAttributes(builder: FluidAttributes.Builder, fluid: Fluid, val nameFun: MaterialFluidAttributes.() -> ITextComponent) : FluidAttributes(builder, fluid) {
    override fun getDisplayName(stack: FluidStack?) = nameFun()

    class Builder(mat: Material, form: Form, baseLocation: ResourceLocation) : FluidAttributes.Builder(baseLocation + "_still", baseLocation + "_flow", { builder: FluidAttributes.Builder, fluid: Fluid -> MaterialFluidAttributes(builder, fluid) { if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else form.localize(mat) } }) {
        init {
            translationKey(form.registryName(mat).toString())
            color(form.color(mat))
            temperature(form.temperature(mat))
            sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY)
            luminosity(((form.temperature(mat) - 500) / 50).coerceIn(0, 15))
            density(ElementUtils.getTotalDensity(mat, form).toInt())
        }
    }
}