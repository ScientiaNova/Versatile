package com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids

import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponentHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.recipestacks.IRecipeStack
import net.minecraftforge.fluids.FluidStack

class FluidInputsHandler(override val value: List<Pair<IRecipeStack<FluidStack>, Float>>) : IRecipeComponentHandler<List<Pair<IRecipeStack<FluidStack>, Float>>> {
    override val pairedComponentType = FluidInputsComponent::class.java
}