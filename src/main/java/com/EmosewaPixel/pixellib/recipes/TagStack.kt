package com.EmosewaPixel.pixellib.recipes

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.tags.Tag

//Tag Stacks are a way of getting an amount of a certain Item Tag. They're meant to be used in Machine Recipes
data class TagStack @JvmOverloads constructor(var tag: Tag<Item>, var count: Int = 1) {
    val isEmpty: Boolean
        get() = count == 0

    fun asItemStack(): ItemStack {
        return ItemStack(tag.allElements.stream().findFirst().orElse(Items.AIR), count)
    }
}