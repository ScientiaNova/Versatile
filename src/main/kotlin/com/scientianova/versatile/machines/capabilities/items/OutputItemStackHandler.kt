package com.scientianova.versatile.machines.capabilities.items

import net.minecraft.item.ItemStack
import net.minecraftforge.items.ItemHandlerHelper
import net.minecraftforge.items.ItemStackHandler

open class OutputItemStackHandler(slotCount: Int) : ItemStackHandler(slotCount), IContainerItemHandler {
    override fun isItemValid(slot: Int, stack: ItemStack) = false

    override fun forceInsertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
        if (stack.isEmpty) return ItemStack.EMPTY

        validateSlotIndex(slot)

        val existing = stacks[slot]

        var limit = getStackLimit(slot, stack)

        if (!existing.isEmpty) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing)) return stack
            limit -= existing.count
        }

        if (limit <= 0) return stack

        val reachedLimit = stack.count > limit

        if (!simulate) {
            if (existing.isEmpty)
                stacks[slot] = if (reachedLimit) ItemHandlerHelper.copyStackWithSize(stack, limit) else stack
            else
                existing.grow(if (reachedLimit) limit else stack.count)
            onContentsChanged(slot)
        }

        return if (reachedLimit) ItemHandlerHelper.copyStackWithSize(stack, stack.count - limit) else ItemStack.EMPTY
    }
}