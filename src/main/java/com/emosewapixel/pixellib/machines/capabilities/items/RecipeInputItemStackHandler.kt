package com.emosewapixel.pixellib.machines.capabilities.items

import com.emosewapixel.pixellib.machines.properties.implementations.recipes.RecipeProperty
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemInputsComponent
import net.minecraft.item.ItemStack

open class RecipeInputItemStackHandler(slots: Int, val recipeProperty: RecipeProperty) : InputItemStackHandler(slots) {
    override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
        val recipeInputs = recipeProperty.value?.get(ItemInputsComponent::class.java)?.value ?: return false
        return recipeInputs.getOrNull(slot)?.first?.matches(stack) ?: false
    }
}