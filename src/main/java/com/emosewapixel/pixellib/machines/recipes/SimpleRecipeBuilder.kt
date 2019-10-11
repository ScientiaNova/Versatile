package com.emosewapixel.pixellib.machines.recipes

//This is a builder for Simple Machine Recipes
class SimpleRecipeBuilder(list: AbstractRecipeList<SimpleMachineRecipe, SimpleRecipeBuilder>) : AbstractRecipeBuilder<SimpleMachineRecipe, SimpleRecipeBuilder>(list) {
    override fun build() = if (canBuild)
        SimpleMachineRecipe(inputs.toTypedArray(), fluidInputs.toTypedArray(), outputs.toTypedArray(), fluidOutputs.toTypedArray(), time)
    else
        SimpleMachineRecipe.EMPTY
}