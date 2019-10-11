package com.emosewapixel.pixellib.machines.recipes

//This is a builder for Energy Machine Recipes
class EnergyRecipeBuilder(list: AbstractRecipeList<EnergyMachineRecipe, EnergyRecipeBuilder>) : AbstractRecipeBuilder<EnergyMachineRecipe, EnergyRecipeBuilder>(list) {
    var energyPerTick: Int = 0

    fun energyPerTick(energy: Int): EnergyRecipeBuilder {
        energyPerTick = energy
        return this
    }

    override fun build() = if (canBuild)
        EnergyMachineRecipe(inputs.toTypedArray(), fluidInputs.toTypedArray(), outputs.toTypedArray(), fluidOutputs.toTypedArray(), time, energyPerTick)
    else
        EnergyMachineRecipe.EMPTY
}