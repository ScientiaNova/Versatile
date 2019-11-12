package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.gui.textures.ProgressBar
import net.minecraft.util.ResourceLocation

//This is a Recipe List for Simple Machine Recipes
class SimpleRecipeList @JvmOverloads constructor(
        name: ResourceLocation,
        maxInputs: Int = 0,
        maxFluidInputs: Int = 0,
        maxOutputs: Int = 0,
        maxFluidOutputs: Int = 0,
        progressBar: ProgressBar = BaseTextures.ARROW_BAR
) : AbstractRecipeList<SimpleMachineRecipe, SimpleRecipeBuilder>(name, maxInputs, maxFluidInputs, maxOutputs, maxFluidOutputs, progressBar) {
    override fun recipeBuilder() = SimpleRecipeBuilder(this)
    override fun build(dsl: SimpleRecipeBuilder.() -> Unit) {
        val builder = SimpleRecipeBuilder(this)
        builder.dsl()
        builder.buildAndRegister()
    }
}