package com.scientianovateam.versatile.items

import com.scientianovateam.versatile.common.extensions.isNotEmpty
import com.scientianovateam.versatile.common.extensions.times
import net.minecraft.item.ItemStack

class ItemStackHolder @JvmOverloads constructor(var stack: ItemStack = ItemStack.EMPTY) {
    //Changes the held stack if it's empty, increments the count if they're the same and otherwise returns the input
    fun changeOrAdd(other: ItemStack): ItemStack {
        when {
            other.isItemEqual(stack) && stack.tag == other.tag -> {
                val added = other.count.coerceAtMost(stack.maxStackSize)
                stack.count += added
                other.shrink(added)
            }
            stack.isEmpty -> {
                stack = other
                return ItemStack.EMPTY
            }
        }
        return other
    }

    var item
        get() = stack.item
        set(value) {
            if (stack.item != value) stack = value * stack.count
        }

    var count
        get() = stack.count
        set(value) {
            stack.count = value
        }

    val isEmpty get() = stack.isEmpty
    val isNotEmpty get() = stack.isNotEmpty
}