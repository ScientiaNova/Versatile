package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.machines.recipes.utility.MachineInput
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.FluidStack

/*
Recipe Lists are objects used for storing recipes with a maximum amount of inputs and outputs.
Recipe Lists are intertwined with Recipe Builders and the Machine Recipes themselves
*/
abstract class AbstractRecipeList<T : SimpleMachineRecipe, B : AbstractRecipeBuilder<T, B>>(val name: ResourceLocation, val maxInputs: Int, val maxFluidInputs: Int, val maxOutputs: Int, val maxFluidOutputs: Int) {
    val recipes = mutableListOf<T>()
    protected val inputMap = mutableMapOf<String, MutableSet<T>>()

    val maxRecipeSlots: Int
        get() = maxInputs + maxOutputs

    val maxRecipeTanks: Int
        get() = maxFluidInputs + maxFluidOutputs

    open fun isInRecipe(stack: ItemStack) = itemKeys.flatMap { it(stack) }.any { it in inputMap }

    open fun isInRecipe(stack: FluidStack) = fluidKeys.flatMap { it(stack) }.any { it in inputMap }

    open fun add(recipe: T) {
        recipes.add(recipe)
        recipe.inputRecipeStacks.forEach {
            val key = it.toString()
            if (key in inputMap)
                inputMap[key]?.add(recipe)
            else
                inputMap[key] = mutableSetOf(recipe)
        }
    }

    open fun find(input: MachineInput): T? {
        val items = input.items.flatMap { item -> itemKeys.map { it(item) } }.map { strs ->
            strs.asSequence().map(inputMap::get).firstOrNull { it != null } ?: mutableSetOf()
        }
        val fluids = input.fluids.flatMap { fluid -> fluidKeys.map { it(fluid) } }.map { strs ->
            strs.asSequence().map(inputMap::get).firstOrNull { it != null } ?: mutableSetOf()
        }

        val intersection = items.firstOrNull() ?: fluids.first()
        items.forEach { intersection.retainAll(it) }
        fluids.forEach { intersection.retainAll(it) }

        return intersection.firstOrNull { it.matches(input) }
    }

    open fun remove(recipe: T) {
        recipes.remove(recipe)
        recipe.inputRecipeStacks.forEach {
            val list = inputMap[it.toString()]
            list?.remove(recipe)
            if (list?.isEmpty() == true)
                inputMap.remove(it.toString())
        }
    }

    abstract fun recipeBuilder(): B
    abstract fun build(dsl: B.() -> Unit)

    companion object {
        @JvmField
        val itemKeys = mutableListOf<(ItemStack) -> Collection<String>>(
                { listOf("item:" + it.item.registryName) },
                { it.item.tags.map { rl -> "item_tag:$rl" } }
        )

        @JvmField
        val fluidKeys = mutableListOf<(FluidStack) -> Collection<String>>(
                { listOf("item:" + it.fluid.registryName) },
                { it.fluid.tags.map { rl -> "item_tag:$rl" } }
        )
    }
}