package com.scientianova.versatile.machines.recipes.components.ingredients.items

import com.scientianova.versatile.machines.recipes.components.IRecipeComponentHandler
import com.scientianova.versatile.machines.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import net.minecraft.item.ItemStack

class ItemInputsHandler(override val value: List<ChancedRecipeStack<ItemStack>>) : IRecipeComponentHandler<List<ChancedRecipeStack<ItemStack>>> {
    override val pairedComponentType = ItemInputsComponent::class.java
}