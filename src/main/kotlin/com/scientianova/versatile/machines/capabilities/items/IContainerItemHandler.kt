package com.scientianova.versatile.machines.capabilities.items

import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler

interface IContainerItemHandler : IItemHandler {
    fun forceExtractItem(slot: Int, amount: Int, simulate: Boolean) = extractItem(slot, amount, simulate)

    fun forceInsertItem(slot: Int, stack: ItemStack, simulate: Boolean) = insertItem(slot, stack, simulate)
}