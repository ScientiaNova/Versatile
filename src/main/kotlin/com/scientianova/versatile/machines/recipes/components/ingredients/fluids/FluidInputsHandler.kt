package com.scientianova.versatile.machines.recipes.components.ingredients.fluids

import com.scientianova.versatile.machines.recipes.components.IRecipeComponentHandler
import com.scientianova.versatile.machines.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import net.minecraftforge.fluids.FluidStack

class FluidInputsHandler(override val value: List<ChancedRecipeStack<FluidStack>>) : IRecipeComponentHandler<List<ChancedRecipeStack<FluidStack>>> {
    override val pairedComponentType = FluidInputsComponent::class.java
}