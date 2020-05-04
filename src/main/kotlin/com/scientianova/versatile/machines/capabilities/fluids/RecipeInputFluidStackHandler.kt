package com.scientianova.versatile.machines.capabilities.fluids

import com.scientianova.versatile.machines.properties.implementations.recipes.RecipeProperty
import com.scientianova.versatile.machines.recipes.components.ingredients.fluids.FluidInputsComponent
import net.minecraftforge.fluids.FluidStack

open class RecipeInputFluidStackHandler(count: Int, val recipeProperty: RecipeProperty, capacity: Int = 10_000) : InputFluidStackHandler(count, capacity) {
    override fun isFluidValid(tank: Int, stack: FluidStack): Boolean {
        val recipeInputs = recipeProperty.value?.get(FluidInputsComponent::class.java)?.value ?: return false
        return recipeInputs.getOrNull(tank)?.matchesWithoutCount(stack) ?: false
    }
}