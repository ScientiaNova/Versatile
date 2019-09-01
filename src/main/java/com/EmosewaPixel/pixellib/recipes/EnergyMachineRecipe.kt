package com.EmosewaPixel.pixellib.recipes

//Energy Machine Recipes are Machine Recipes that take power
class EnergyMachineRecipe(input: Array<Any>, consumeChances: Array<Float>, output: Array<Any>, outputChances: Array<Float>, time: Int, var energyPerTick: Int) : SimpleMachineRecipe(input, consumeChances, output, outputChances, time) {
    override val isEmpty = this === EMPTY

    companion object {
        @JvmField
        var EMPTY = EnergyMachineRecipe(arrayOf(), arrayOf(), arrayOf(), arrayOf(), 0, 0)
    }
}