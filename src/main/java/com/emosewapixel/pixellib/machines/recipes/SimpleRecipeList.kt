package com.emosewapixel.pixellib.machines.recipes

import net.minecraft.util.ResourceLocation

//This is a Recipe List for Simple Machine Recipes
class SimpleRecipeList(name: ResourceLocation, maxInputs: Int, maxFluidInputs: Int, maxOutputs: Int, maxFluidOutputs: Int) : AbstractRecipeList<SimpleMachineRecipe, SimpleRecipeBuilder>(name, maxInputs, maxFluidInputs, maxOutputs, maxFluidOutputs) {
    override fun recipeBuilder() = SimpleRecipeBuilder(this)
    override fun build(dsl: SimpleRecipeBuilder.() -> Unit) = SimpleRecipeBuilder(this).dsl()
}