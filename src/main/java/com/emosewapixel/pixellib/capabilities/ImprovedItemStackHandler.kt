package com.emosewapixel.pixellib.capabilities

import net.minecraft.item.ItemStack
import net.minecraftforge.items.ItemStackHandler

//Improved Item Stack Handlers are Item Stack Handlers that can blacklist slots for insertion and extraction
open class ImprovedItemStackHandler @JvmOverloads constructor(slots: Int, noInput: Array<Int>? = null, noOutput: Array<Int>? = null) : ItemStackHandler(slots) {
    protected var noInputSlots: List<Int> = listOf(*noInput!!)
    protected var noOutputSlots: List<Int> = listOf(*noOutput!!)

    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean) = if (!isItemValid(slot, stack)) stack else super.insertItem(slot, stack, simulate)

    override fun isItemValid(slot: Int, itemStack: ItemStack) = !noInputSlots.contains(slot)

    override fun extractItem(slot: Int, amount: Int, simulate: Boolean) = if (noOutputSlots.contains(slot)) ItemStack.EMPTY else super.extractItem(slot, amount, simulate)
}