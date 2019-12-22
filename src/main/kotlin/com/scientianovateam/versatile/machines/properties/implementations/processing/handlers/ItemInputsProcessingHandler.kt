package com.scientianovateam.versatile.machines.properties.implementations.processing.handlers

import com.scientianovateam.versatile.common.extensions.toList
import com.scientianovateam.versatile.machines.properties.implementations.items.TEItemInputProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.IProcessingHandler
import com.scientianovateam.versatile.machines.recipes.Recipe
import com.scientianovateam.versatile.machines.recipes.components.ingredients.items.ItemInputsComponent
import java.util.*

class ItemInputsProcessingHandler(val property: TEItemInputProperty) : IProcessingHandler {
    override fun canStartProcessingStandard(recipe: Recipe): Boolean {
        val recipeInputs = recipe[ItemInputsComponent::class.java]?.value ?: return true
        val inputStacks = property.value.toList().toMutableList()
        recipeInputs.forEach {
            val matching = inputStacks.firstOrNull(it::matches) ?: return false
            inputStacks.remove(matching)
        }
        return true
    }

    override fun canStartProcessingAutomation(recipe: Recipe): Boolean {
        val recipeInputs = recipe[ItemInputsComponent::class.java]?.value ?: return true
        return recipeInputs.size <= property.value.slots && recipeInputs.indices.all {
            recipeInputs[it].count <= property.value.getStackInSlot(it).count
        }
    }

    override fun startProcessingStandard(recipe: Recipe) {
        val recipeInputs = recipe[ItemInputsComponent::class.java]?.value ?: return
        val inputStacks = property.value.toList().toMutableList()
        recipeInputs.forEach {
            val matching = inputStacks.firstOrNull(it::matches) ?: return@forEach
            matching.shrink(it.count)
            inputStacks.remove(matching)
        }
    }

    override fun startProcessingAutomation(recipe: Recipe) {
        val recipeInputs = recipe[ItemInputsComponent::class.java]?.value ?: return
        recipeInputs.indices.forEach {
            if (recipeInputs[it].chance > Random().nextFloat())
                property.value.getStackInSlot(it).shrink(recipeInputs[it].count)
        }
    }
}