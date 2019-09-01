package com.EmosewaPixel.pixellib.recipes

import net.minecraft.util.ResourceLocation

//This is a Recipe List for Simple Machine Recipes
class SimpleRecipeList(name: ResourceLocation, maxInputs: Int, maxOutputs: Int) : AbstractRecipeList<SimpleMachineRecipe, SimpleRecipeBuilder>(name, maxInputs, maxOutputs) {
    override fun recipeBuilder() = SimpleRecipeBuilder(this)
}
