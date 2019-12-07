package com.emosewapixel.pixellib.machines.capabilities.items

import net.minecraft.item.ItemStack
import net.minecraftforge.items.ItemStackHandler

open class OutputItemStackHandler(slotCount: Int) : ItemStackHandler(slotCount) {
    override fun isItemValid(slot: Int, stack: ItemStack) = false
}