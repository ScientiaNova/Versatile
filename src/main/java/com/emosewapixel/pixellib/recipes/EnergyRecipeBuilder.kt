package com.emosewapixel.pixellib.recipes

//This is a builder for Energy Machine Recipes
class EnergyRecipeBuilder(list: AbstractRecipeList<EnergyMachineRecipe, EnergyRecipeBuilder>) : AbstractRecipeBuilder<EnergyMachineRecipe, EnergyRecipeBuilder>(list) {
    protected var energyPerTick: Int = 0
        private set

    fun energyPerTick(energy: Int): EnergyRecipeBuilder {
        energyPerTick = energy
        return this
    }

    override fun build() =
            if (inputs.size <= recipeList.maxInputs && outputs.size <= recipeList.maxOutputs) EnergyMachineRecipe(inputs.toTypedArray(), consumeChances.toTypedArray(), outputs.toTypedArray(), outputChances.toTypedArray(), time, energyPerTick) else EnergyMachineRecipe.EMPTY
}