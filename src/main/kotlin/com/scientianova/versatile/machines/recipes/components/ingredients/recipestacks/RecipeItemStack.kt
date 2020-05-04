package com.scientianova.versatile.machines.recipes.components.ingredients.recipestacks

import com.scientianova.versatile.common.extensions.toStack
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class RecipeItemStack(val stack: ItemStack) : IRecipeStack<ItemStack> {
    override val count = stack.count

    override val stacks = listOf(stack.copy())

    override fun matches(other: ItemStack) = stack.isItemEqual(other) && count <= other.count

    override fun matchesWithoutCount(other: ItemStack) = stack.isItemEqual(other)

    override fun toString() = "item:" + stack.item.registryName

    override fun hashCode() = toString().hashCode()

    override fun equals(other: Any?) = other is RecipeItemStack && ItemStack.areItemStacksEqual(stack, other.stack)

    companion object {
        @JvmField
        val EMPTY = RecipeItemStack(ItemStack.EMPTY)
    }
}

operator fun MutableCollection<IRecipeStack<ItemStack>>.plusAssign(stack: ItemStack) = plusAssign(RecipeItemStack(stack))
fun Item.toRStack(count: Int = 1) = RecipeItemStack(this.toStack(count))
fun ItemStack.r() = RecipeItemStack(this)