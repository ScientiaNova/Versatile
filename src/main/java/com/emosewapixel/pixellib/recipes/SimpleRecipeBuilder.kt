package com.emosewapixel.pixellib.recipes

//This is a builder for Simple Machine Recipes
class SimpleRecipeBuilder(list: AbstractRecipeList<SimpleMachineRecipe, SimpleRecipeBuilder>) : AbstractRecipeBuilder<SimpleMachineRecipe, SimpleRecipeBuilder>(list) {
    override fun build() = SimpleMachineRecipe(inputs.map { it.first }.toTypedArray(), inputs.map { it.second }.toTypedArray(), fluidInputs.toTypedArray(), outputs.map { it.first }.toTypedArray(), outputs.map { it.second }.toTypedArray(), fluidOutputs.toTypedArray(), time)
}