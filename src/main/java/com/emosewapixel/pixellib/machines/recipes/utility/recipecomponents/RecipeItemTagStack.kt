package com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents

import com.emosewapixel.pixellib.machines.recipes.utility.TagStack
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class RecipeItemTagStack(stack: TagStack<Item>) : IRecipeStack<ItemStack> {
    val tag = stack.tag

    override val count = stack.count

    override val stacks
        get() = tag.allElements.map { it to count }

    override fun matches(other: ItemStack) = count <= other.count && other.item in tag

    override fun toString() = "item_tag:" + tag.id
}

operator fun MutableCollection<IRecipeStack<ItemStack>>.plusAssign(stack: TagStack<Item>) = plusAssign(RecipeItemTagStack(stack))