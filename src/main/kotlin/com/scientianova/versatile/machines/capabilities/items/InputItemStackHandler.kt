package com.scientianova.versatile.machines.capabilities.items

import net.minecraft.item.ItemStack
import net.minecraftforge.items.ItemStackHandler

open class InputItemStackHandler(slotCount: Int) : ItemStackHandler(slotCount), IContainerItemHandler {
    override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack = ItemStack.EMPTY

    override fun forceExtractItem(slot: Int, amount: Int, simulate: Boolean) = super.extractItem(slot, amount, simulate)
}