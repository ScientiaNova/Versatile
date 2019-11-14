package com.emosewapixel.pixellib.machines.gui.slots

import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack

object SlotUtils {
    fun mergeWithSlots(stack: ItemStack, slots: List<Slot>): Boolean {
        var successful = false
        slots.forEach {
            if (it.hasStack) {
                val stackInCurrent = it.stack
                if (stack.item != stackInCurrent.item || stack.tag != stackInCurrent.tag) return@forEach
                val mergeCount = (it.getItemStackLimit(stack).coerceAtMost(stackInCurrent.maxStackSize) - stackInCurrent.count).coerceAtMost(stack.count)
                if (mergeCount == 0) return@forEach
                stack.shrink(mergeCount)
                stackInCurrent.grow(mergeCount)
                successful = true
                if (stack.isEmpty) return true
            } else {
                val mergeCount = it.getItemStackLimit(stack).coerceAtMost(stack.count)
                if (mergeCount == 0) return@forEach
                it.putStack(stack.split(mergeCount))
                successful = true
                if (stack.isEmpty) return true
            }
        }
        return successful
    }
}