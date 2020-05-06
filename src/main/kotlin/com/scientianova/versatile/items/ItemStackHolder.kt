package com.scientianova.versatile.items

import com.scientianova.versatile.common.extensions.isNotEmpty
import com.scientianova.versatile.common.extensions.times
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class ItemStackHolder @JvmOverloads constructor(var stack: ItemStack = ItemStack.EMPTY) {
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

    var item: Item
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