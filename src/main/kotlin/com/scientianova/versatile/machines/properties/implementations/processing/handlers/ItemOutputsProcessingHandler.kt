package com.scientianova.versatile.machines.properties.implementations.processing.handlers

import com.scientianova.versatile.machines.properties.implementations.items.TEItemOutputProperty
import com.scientianova.versatile.machines.properties.implementations.processing.IProcessingHandler
import com.scientianova.versatile.machines.recipes.Recipe
import com.scientianova.versatile.machines.recipes.components.ingredients.items.ItemOutputsComponent
import net.minecraft.item.ItemStack
import java.util.*

class ItemOutputsProcessingHandler(val property: TEItemOutputProperty) : IProcessingHandler {
    override fun canStartProcessingStandard(recipe: Recipe): Boolean {
        val recipeOutputs = recipe[ItemOutputsComponent::class.java]?.value ?: return true
        val handlerClone = property.clone().value
        return recipeOutputs.all {
            val stack = it.stacks.firstOrNull() ?: ItemStack.EMPTY
            (0 until handlerClone.slots).fold(stack) { acc, slotId -> handlerClone.forceInsertItem(slotId, acc, false) }.isEmpty
        }
    }

    override fun canStartProcessingAutomation(recipe: Recipe): Boolean {
        val recipeOutputs = recipe[ItemOutputsComponent::class.java]?.value ?: return true
        return recipeOutputs.size <= property.value.slots && recipeOutputs.indices.all {
            recipeOutputs[it].count <= property.value.getSlotLimit(it) - property.value.getStackInSlot(it).count
        }
    }

    override fun finishProcessingStandard(recipe: Recipe) {
        val recipeOutputs = recipe[ItemOutputsComponent::class.java]?.value ?: return
        recipeOutputs.forEach {
            if (it.chance <= Random().nextFloat()) return@forEach
            val stack = it.stacks.firstOrNull() ?: ItemStack.EMPTY
            (0 until property.value.slots).fold(stack) { acc, slotId -> property.value.forceInsertItem(slotId, acc, false) }
        }
    }

    override fun finishProcessingAutomation(recipe: Recipe) {
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