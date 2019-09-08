package com.emosewapixel.pixellib.recipes

//This is a builder for Simple Machine Recipes
class SimpleRecipeBuilder(list: AbstractRecipeList<SimpleMachineRecipe, SimpleRecipeBuilder>) : AbstractRecipeBuilder<SimpleMachineRecipe, SimpleRecipeBuilder>(list) {
    override fun build() = if (inputs.size <= recipeList.maxInputs && outputs.size <= recipeList.maxOutputs) SimpleMachineRecipe(inputs.toTypedArray(), consumeChances.toTypedArray(), outputs.toTypedArray(), outputChances.toTypedArray(), time) else SimpleMachineRecipe.EMPTY
}