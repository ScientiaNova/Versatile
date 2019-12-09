package com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids

import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponentHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.recipestacks.IRecipeStack
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.utility.WeightedMap
import net.minecraftforge.fluids.FluidStack

class FluidOutputsHandler(override val value: List<WeightedMap<IRecipeStack<FluidStack>>>) : IRecipeComponentHandler<List<WeightedMap<IRecipeStack<FluidStack>>>> {
    override val pairedComponentType = FluidOutputsComponent::class.java
}