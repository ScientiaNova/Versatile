package com.scientianova.versatile.machines.capabilities.items

import net.minecraft.item.ItemStack

open class NonRepeatingInputItemStackHandler(slots: Int) : InputItemStackHandler(slots) {
    override fun isItemValid(slot: Int, stack: ItemStack) = !stacks.withIndex().any {
        it.index != slot && it.value.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(it.value, stack)
    } && super.isItemValid(slot, stack)
}