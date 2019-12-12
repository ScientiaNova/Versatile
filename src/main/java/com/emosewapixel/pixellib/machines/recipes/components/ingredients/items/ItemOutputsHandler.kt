package com.emosewapixel.pixellib.machines.recipes.components.ingredients.items

import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponentHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.recipestacks.IRecipeStack
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.utility.WeightedMap
import net.minecraft.item.ItemStack

class ItemOutputsHandler(override val value: List<WeightedMap<out IRecipeStack<ItemStack>>>) : IRecipeComponentHandler<List<WeightedMap<out IRecipeStack<ItemStack>>>> {
    override val pairedComponentType = ItemOutputsComponent::class.java
}