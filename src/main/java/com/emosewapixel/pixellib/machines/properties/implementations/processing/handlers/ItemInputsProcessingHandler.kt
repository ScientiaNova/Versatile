package com.emosewapixel.pixellib.machines.properties.implementations.processing.handlers

import com.emosewapixel.pixellib.machines.properties.implementations.items.TEItemInputProperty
import com.emosewapixel.pixellib.machines.properties.implementations.processing.IProcessingHandler
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemInputsComponent
import java.util.*

class ItemInputsProcessingHandler(val property: TEItemInputProperty) : IProcessingHandler {
    override fun canStartProcessing(recipe: Recipe): Boolean {
        val recipeInputs = recipe[ItemInputsComponent::class.java]?.value ?: return true
        return recipeInputs.size <= property.value.slots && recipeInputs.indices.all {
            recipeInputs[it].count <= property.value.getStackInSlot(it).count
        }
    }

    override fun startProcessing(recipe: Recipe) {
        val recipeInputs = recipe[ItemInputsComponent::class.java]?.value ?: return
        recipeInputs.indices.forEach {
            if (recipeInputs[it].chance > Random().nextFloat())
                property.value.getStackInSlot(it).shrink(recipeInputs[it].count)
        }
    }
}