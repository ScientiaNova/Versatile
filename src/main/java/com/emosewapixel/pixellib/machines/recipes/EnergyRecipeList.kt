package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.gui.textures.ProgressBar
import net.minecraft.util.ResourceLocation

//This is a Recipe List for Energy Machine Recipes
class EnergyRecipeList @JvmOverloads constructor(
        name: ResourceLocation,
        maxInputs: Int = 0,
        maxFluidInputs: Int = 0,
        maxOutputs: Int = 0,
        maxFluidOutputs: Int = 0,
        progressBar: ProgressBar = BaseTextures.ARROW_BAR
) : AbstractRecipeList<EnergyMachineRecipe, EnergyRecipeBuilder>(name, maxInputs, maxFluidInputs, maxOutputs, maxFluidOutputs, progressBar) {
    override fun recipeBuilder() = EnergyRecipeBuilder(this)
    override fun build(dsl: EnergyRecipeBuilder.() -> Unit) {
        val builder = EnergyRecipeBuilder(this)
        builder.dsl()
        builder.buildAndRegister()
    }
}