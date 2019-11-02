package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.machines.recipes.utility.WeightedMap
import com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents.IRecipeStack
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

//Energy Machine Recipes are Machine Recipes that take power
class EnergyMachineRecipe(inputs: Array<Pair<IRecipeStack<ItemStack>, Float>>, fluidInputs: Array<Pair<IRecipeStack<FluidStack>, Float>>, outputs: Array<WeightedMap<out IRecipeStack<ItemStack>>>, fluidOutputs: Array<WeightedMap<out IRecipeStack<FluidStack>>>, time: Int, val energyPerTick: Int) : SimpleMachineRecipe(inputs, fluidInputs, outputs, fluidOutputs, time) {
    override val isEmpty = this === EMPTY

    companion object {
        @JvmField
        var EMPTY = EnergyMachineRecipe(emptyArray(), emptyArray(), emptyArray(), emptyArray(), 0, 0)
    }
}