package com.emosewapixel.pixellib.machines.recipes.components.ingredients.items

import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponentHandler
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.recipestacks.IRecipeStack
import net.minecraft.item.ItemStack

class ItemInputsHandler(override val value: List<Pair<IRecipeStack<ItemStack>, Float>>) : IRecipeComponentHandler<List<Pair<IRecipeStack<ItemStack>, Float>>> {
    override val pairedComponentType = ItemInputsComponent::class.java
}