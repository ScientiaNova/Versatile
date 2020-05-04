package com.scientianova.versatile.common.extensions

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraftforge.items.ItemHandlerHelper

fun PlayerInventory.insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
    if (stack.isEmpty) return ItemStack.EMPTY

    val existing = this.getStackInSlot(slot)

    var limit = stack.maxStackSize

    if (!existing.isEmpty) {
        if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
            return stack

        limit -= existing.count
    }

    if (limit <= 0)
        return stack

    val reachedLimit = stack.count > limit

    if (!simulate) {
        if (existing.isEmpty)
            this.setInventorySlotContents(slot,
                    if (reachedLimit) ItemHandlerHelper.copyStackWithSize(stack, limit)
                    else stack
            )
        else
            existing.grow(if (reachedLimit) limit else stack.count)
    }

    return if (reachedLimit) ItemHandlerHelper.copyStackWithSize(stack, stack.count - limit) else ItemStack.EMPTY
}

fun PlayerInventory.extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
    if (amount == 0) return ItemStack.EMPTY

    val existing = this.getStackInSlot(slot)

    if (existing.isEmpty) return ItemStack.EMPTY

    val toExtract = amount.coerceAtMost(existing.maxStackSize)

    return if (existing.count <= toExtract) {
        if (!simulate)
            this.setInventorySlotContents(slot, ItemStack.EMPTY)
        existing
    } else {
        if (!simulate)
            this.setInventorySlotContents(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.count - toExtract))

        ItemHandlerHelper.copyStackWithSize(existing, toExtract)
    }
}