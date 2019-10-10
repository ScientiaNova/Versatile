package com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents

import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient

interface IRecipeStack<T> {
    val count: Int

    val stacks: List<T>

    fun matches(other: T): Boolean

    override fun toString(): String
}

fun IRecipeStack<ItemStack>.toIngredient() = Ingredient.fromStacks(*stacks.toTypedArray())