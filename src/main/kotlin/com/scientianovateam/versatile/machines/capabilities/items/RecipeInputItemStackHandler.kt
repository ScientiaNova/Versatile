package com.scientianovateam.versatile.machines.capabilities.items

import com.scientianovateam.versatile.machines.properties.implementations.recipes.RecipeProperty
import com.scientianovateam.versatile.recipes.components.ingredients.items.input.ItemInputsComponent
import net.minecraft.item.ItemStack

open class RecipeInputItemStackHandler(slots: Int, val recipeProperty: RecipeProperty) : InputItemStackHandler(slots) {
    override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
        val recipeInputs = recipeProperty.value?.get(ItemInputsComponent::class.java)?.value ?: return false
        return recipeInputs.getOrNull(slot)?.matchesWithoutCount(stack) ?: false
    }
}