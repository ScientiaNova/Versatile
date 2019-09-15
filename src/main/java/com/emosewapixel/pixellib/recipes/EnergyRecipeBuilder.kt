package com.emosewapixel.pixellib.recipes

//This is a builder for Energy Machine Recipes
class EnergyRecipeBuilder(list: AbstractRecipeList<EnergyMachineRecipe, EnergyRecipeBuilder>) : AbstractRecipeBuilder<EnergyMachineRecipe, EnergyRecipeBuilder>(list) {
    var energyPerTick: Int = 0

    fun energyPerTick(energy: Int): EnergyRecipeBuilder {
        energyPerTick = energy
        return this
    }

    override fun build() = EnergyMachineRecipe(inputs.map { it.first }.toTypedArray(), inputs.map { it.second }.toTypedArray(), fluidInputs.toTypedArray(), outputs.map { it.first }.toTypedArray(), outputs.map { it.second }.toTypedArray(), fluidOutputs.toTypedArray(), time, energyPerTick)
}