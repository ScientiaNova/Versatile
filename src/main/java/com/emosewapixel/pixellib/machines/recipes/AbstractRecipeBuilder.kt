package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.PixelLib
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

//Recipe Builders are builders used for easily creating Machine Recipes
abstract class AbstractRecipeBuilder<T : SimpleMachineRecipe, R : AbstractRecipeBuilder<T, R>>(protected val recipeList: AbstractRecipeList<T, R>) {
    protected val inputs = mutableListOf<Pair<Any, Float>>()
    val fluidInputs = mutableListOf<FluidStack>()
    protected val outputs = mutableListOf<Pair<Any, Float>>()
    val fluidOutputs = mutableListOf<FluidStack>()
    var time = 0

    operator fun MutableList<Pair<Any, Float>>.plusAssign(stack: ItemStack): Unit = plusAssign(stack to 1f)
    operator fun MutableList<Pair<Any, Float>>.plusAssign(stack: TagStack): Unit = plusAssign(stack to 1f)

    fun input(vararg inputs: Any): R {
        this.inputs += (listOf(*inputs)).map { it to 1f }
        return this as R
    }

    fun potentiallyConsumable(input: Any, consumeChance: Float): R {
        this.inputs += input to consumeChance
        return this as R
    }

    fun output(vararg outputs: Any): R {
        this.outputs += (listOf(*outputs)).map { it to 1f }
        return this as R
    }

    fun chancedOutput(output: Any, chance: Float): R {
        this.inputs += output to chance
        return this as R
    }

    infix fun time(amount: Int): R {
        time = amount
        return this as R
    }

    protected abstract fun build(): T

    fun buildAndRegister() {
        if (!build().isEmpty)
            recipeList.add(build())
        else
            PixelLib.LOGGER.error("Recipe with output {} is empty", outputs)
    }
}
