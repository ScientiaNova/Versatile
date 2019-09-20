package com.emosewapixel.pixellib.machines.recipes

import net.minecraftforge.fluids.FluidStack

//Energy Machine Recipes are Machine Recipes that take power
class EnergyMachineRecipe(inputs: Array<Any>, consumeChances: Array<Float>, fluidInputs: Array<FluidStack>, outputs: Array<Any>, outputChances: Array<Float>, fluidOutputs: Array<FluidStack>, time: Int, var energyPerTick: Int) : SimpleMachineRecipe(inputs, consumeChances, fluidInputs, outputs, outputChances, fluidOutputs, time) {
    override val isEmpty = this === EMPTY

    companion object {
        @JvmField
        var EMPTY = EnergyMachineRecipe(arrayOf(), arrayOf(), arrayOf(), arrayOf(), arrayOf(), arrayOf(), 0, 0)
    }
}