package com.emosewapixel.pixellib.machines.properties.implementations.processing.handlers

import com.emosewapixel.pixellib.machines.properties.implementations.items.TEItemOutputProperty
import com.emosewapixel.pixellib.machines.properties.implementations.processing.IProcessingHandler
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemOutputsComponent
import net.minecraft.item.ItemStack
import java.util.*

class ItemOutputsProcessingHandler(val property: TEItemOutputProperty) : IProcessingHandler {
    override fun canStartProcessing(recipe: Recipe): Boolean {
        val recipeOutputs = recipe[ItemOutputsComponent::class.java]?.value ?: return true
        return recipeOutputs.size <= property.value.slots && recipeOutputs.indices.all {
            recipeOutputs[it].count <= property.value.getSlotLimit(it) - property.value.getStackInSlot(it).count
        }
    }

    override fun finishProcessing(recipe: Recipe) {
        val recipeOutputs = recipe[ItemOutputsComponent::class.java]?.value ?: return
        recipeOutputs.indices.forEach {
            if (recipeOutputs[it].chance <= Random().nextFloat()) return@forEach
            val stack = property.value.getStackInSlot(it)
            if (stack.isEmpty)
                property.value.setStackInSlot(it, recipeOutputs[it].stacks.firstOrNull()?.copy() ?: ItemStack.EMPTY)
            else
                stack.grow(recipeOutputs[it].count)
        }
    }
}