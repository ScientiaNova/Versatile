package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.machines.recipes.utility.WeightedMap
import com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents.IRecipeStack
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

    open val isEmpty: Boolean
        get() = this === EMPTY

    fun getInput(index: Int) = inputs[index]

    fun getConsumeChance(index: Int) = inputs[index].second

    fun getInputCount(index: Int) = inputs[index].first.count

    fun itemBelongsInRecipe(stack: ItemStack) = if (stack.isEmpty) false else inputs.any { recipe ->
        recipe.first.stacks.any(stack::isItemEqual)
    }

    companion object {
        @JvmField
        val EMPTY = SimpleMachineRecipe(emptyArray(), emptyArray(), emptyArray(), emptyArray(), 0)
    }
}