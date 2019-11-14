package com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents

import com.emosewapixel.pixellib.extensions.toStack
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class RecipeItemStack(val stack: ItemStack) : IRecipeStack<ItemStack> {
    override val count = stack.count

    override val stacks = listOf(stack.copy())

    override fun matches(other: ItemStack) = stack.isItemEqual(other) && count <= other.count

    override fun toString() = "item:" + stack.item.registryName

    companion object {
        @JvmField
        val EMPTY = RecipeItemStack(ItemStack.EMPTY)
    }
}

operator fun MutableCollection<IRecipeStack<ItemStack>>.plusAssign(stack: ItemStack) = plusAssign(RecipeItemStack(stack))
fun Item.toRStack(count: Int = 1000) = RecipeItemStack(this.toStack(count))
fun ItemStack.r() = RecipeItemStack(this)