package com.emosewapixel.pixellib.machines.properties.implementations.processing.handlers

import com.emosewapixel.pixellib.extensions.toList
import com.emosewapixel.pixellib.machines.properties.implementations.fluids.TEFluidInputProperty
import com.emosewapixel.pixellib.machines.properties.implementations.processing.IProcessingHandler
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidInputsComponent
import java.util.*

class FluidInputsProcessingHandler(val property: TEFluidInputProperty) : IProcessingHandler {
    override fun canStartProcessingStandard(recipe: Recipe): Boolean {
        val recipeInputs = recipe[FluidInputsComponent::class.java]?.value ?: return true
        val inputStacks = property.value.toList().toMutableList()
        recipeInputs.forEach {
            val matching = inputStacks.firstOrNull(it::matches) ?: return false
            inputStacks.remove(matching)
        }
        return true
    }

    override fun canStartProcessingAutomation(recipe: Recipe): Boolean {
        val recipeInputs = recipe[FluidInputsComponent::class.java]?.value ?: return true
        return recipeInputs.size <= property.value.tanks && recipeInputs.indices.all {
            recipeInputs[it].count <= property.value.getFluidInTank(it).amount
        }
    }

    override fun startProcessingStandard(recipe: Recipe) {
        val recipeInputs = recipe[FluidInputsComponent::class.java]?.value ?: return
        val inputStacks = property.value.toList().toMutableList()
        recipeInputs.forEach {
            val matching = inputStacks.firstOrNull(it::matches) ?: return@forEach
            matching.shrink(it.count)
            inputStacks.remove(matching)
        }
        return
    }

    override fun startProcessingAutomation(recipe: Recipe) {
        val recipeInputs = recipe[FluidInputsComponent::class.java]?.value ?: return
        recipeInputs.indices.forEach {
            if (recipeInputs[it].chance > Random().nextFloat())
                property.value.getFluidInTank(it).shrink(recipeInputs[it].count)
        }
    }
}