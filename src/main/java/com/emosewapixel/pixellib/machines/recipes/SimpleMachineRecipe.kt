package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.extensions.isNotEmpty
import com.emosewapixel.pixellib.machines.recipes.utility.WeightedMap
import com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents.IRecipeStack
import com.emosewapixel.pixellib.machines.recipes.utility.MachineInput
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

//Simple Machine Recipes are the most basic Machine Recipes, only having inputs, outputs and a processing time
open class SimpleMachineRecipe(val inputs: Array<Pair<IRecipeStack<ItemStack>, Float>>, val fluidInputs: Array<Pair<IRecipeStack<FluidStack>, Float>>, val outputs: Array<WeightedMap<IRecipeStack<ItemStack>>>, val fluidOutputs: Array<WeightedMap<IRecipeStack<FluidStack>>>, var time: Int) {
    val outputStackLists: List<List<ItemStack>>
        get() = outputs.flatMap { it.values.map(IRecipeStack<ItemStack>::stacks) }

    val outputStacks: List<ItemStack>
        get() = outputStackLists.map(List<ItemStack>::first)

    val inputStackLists: List<List<ItemStack>>
        get() = inputs.map { it.first.stacks }

    val fluidInputStackLists: List<List<FluidStack>>
        get() = fluidInputs.map { it.first.stacks }

    val inputItemRecipeStacks
        get() = inputs.map(Pair<IRecipeStack<ItemStack>, Float>::first)

    val inputFluidRecipeStacks
        get() = fluidInputs.map(Pair<IRecipeStack<FluidStack>, Float>::first)

    val inputRecipeStacks: List<IRecipeStack<*>>
        get() = inputItemRecipeStacks + inputFluidRecipeStacks

    open val isEmpty: Boolean
        get() = this === EMPTY

    fun getInput(index: Int) = inputs[index]

    fun getConsumeChance(index: Int) = inputs[index].second

    fun getInputCount(index: Int) = inputs[index].first.count

    open fun matches(input: MachineInput): Boolean {
        val inputItems = input.items.filter(ItemStack::isNotEmpty)
        val inputFluids = input.fluids.filter(FluidStack::isNotEmpty)

        val sizesMatch = inputItems.size == inputs.size && inputFluids.size == fluidInputs.size
        if (!sizesMatch) return false
        
        // fluids first because there's less options for this
        val recipeFluidStacks = inputFluidRecipeStacks.toMutableList()
        val fluidsMatch = inputFluids.all { fluid ->
            val match = recipeFluidStacks.firstOrNull { it.matches(fluid) }
            if (match == null) {
                false
            } else {
                recipeFluidStacks.remove(match)
                true
            }
        }
        if (!fluidsMatch) return false
        
        val recipeItemStacks = inputItemRecipeStacks.toMutableList()
        val itemsMatch = inputItems.all { item ->
            val match = recipeItemStacks.firstOrNull { it.matches(item) }
            if (match == null) {
                false
            } else {
                recipeItemStacks.remove(match)
                true
            }
        }
        if (!itemsMatch) return false
        
        return true
    }

    companion object {
        @JvmField
        val EMPTY = SimpleMachineRecipe(emptyArray(), emptyArray(), emptyArray(), emptyArray(), 0)
    }
}