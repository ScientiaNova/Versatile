package com.scientianovateam.versatile.machines.recipes.components.ingredients.items

import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import net.minecraft.item.ItemStack

class ItemOutputsHandler(override val value: List<ChancedRecipeStack<ItemStack>>) : IRecipeComponentHandler<List<ChancedRecipeStack<ItemStack>>> {
    override val pairedComponentType = ItemOutputsComponent::class.java
}