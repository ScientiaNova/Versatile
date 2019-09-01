package com.EmosewaPixel.pixellib.recipes

import com.EmosewaPixel.pixellib.PixelLib
import java.util.*

//Recipe Builders are builders used for easily creating Machine Recipes
abstract class AbstractRecipeBuilder<T : SimpleMachineRecipe, R : AbstractRecipeBuilder<T, R>>(protected val recipeList: AbstractRecipeList<T, R>) {
    protected val inputs = ArrayList<Any>()
    protected val consumeChances = ArrayList<Float>()
    protected val outputs = ArrayList<Any>()
    protected val outputChances = ArrayList<Float>()
    protected var time = 0
        private set

    fun input(vararg inputs: Any): R {
        this.inputs.addAll(listOf(*inputs))
        repeat(inputs.size) { consumeChances.add(1f) }
        return this as R
    }

    fun potentiallyConsumable(input: Any, consumeChance: Float): R {
        this.inputs.add(input)
        consumeChances.add(consumeChance)
        return this as R
    }

    fun notConsumable(vararg inputs: Any): R {
        this.inputs.addAll(listOf(*inputs))
        repeat(inputs.size) { consumeChances.add(0f) }
        return this as R
    }

    fun output(vararg outputs: Any): R {
        this.outputs.addAll(listOf(*outputs))
        repeat(outputs.size) { outputChances.add(1.0f) }
        return this as R
    }

    fun chancedOutput(output: Any, chance: Float): R {
        this.outputs.add(output)
        outputChances.add(chance)
        return this as R
    }

    fun time(amount: Int): R {
        time = amount
        return this as R
    }

    abstract fun build(): T

    fun buildAndRegister() {
        if (!build().isEmpty)
            recipeList.add(build())
        else
            PixelLib.LOGGER.error("Recipe with output {} is empty", outputs)
    }
}
