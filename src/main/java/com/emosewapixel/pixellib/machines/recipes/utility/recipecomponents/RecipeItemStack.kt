package com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents

import net.minecraft.item.ItemStack

class RecipeItemStack(val stack: ItemStack) : IRecipeStack<ItemStack> {
    override val count = stack.count

    override val stacks = listOf(stack)

    override fun matches(other: ItemStack) = stack.isItemEqual(other) && count <= other.count

    companion object {
        @JvmField
        val EMPTY = RecipeItemStack(ItemStack.EMPTY)
    }
}

operator fun MutableCollection<IRecipeStack<ItemStack>>.plusAssign(stack: ItemStack) = plusAssign(RecipeItemStack(stack))