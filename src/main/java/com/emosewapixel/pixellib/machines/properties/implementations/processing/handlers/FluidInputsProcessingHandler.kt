package com.emosewapixel.pixellib.machines.properties.implementations.processing.handlers

import com.emosewapixel.pixellib.machines.properties.implementations.fluids.TEFluidInputProperty
import com.emosewapixel.pixellib.machines.properties.implementations.processing.IProcessingHandler
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidInputsComponent
import java.util.*

class FluidInputsProcessingHandler(val property: TEFluidInputProperty) : IProcessingHandler {
    override fun canStartProcessing(recipe: Recipe): Boolean {
        val recipeInputs = recipe[FluidInputsComponent::class.java]?.value ?: return true
        return recipeInputs.size <= property.value.tanks && recipeInputs.indices.all {
            recipeInputs[it].count <= property.value.getFluidInTank(it).amount
        }
    }

    override fun startProcessing(recipe: Recipe) {
        val recipeInputs = recipe[FluidInputsComponent::class.java]?.value ?: return
        recipeInputs.indices.forEach {
            if (recipeInputs[it].chance > Random().nextFloat())
                property.value.getFluidInTank(it).shrink(recipeInputs[it].count)
        }
    }
}