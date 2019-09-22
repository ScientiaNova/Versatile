package com.emosewapixel.pixellib.machines.capabilities

import net.minecraft.item.ItemStack
import net.minecraftforge.items.ItemStackHandler

//Improved Item Stack Handlers are Item Stack Handlers that can blacklist slots for insertion and extraction
open class ImprovedItemStackHandler @JvmOverloads constructor(slots: Int, val noOutputSlots: Array<Int> = emptyArray(), val noInputSlots: Array<Int> = emptyArray()) : ItemStackHandler(slots) {
    constructor(slots: Int, noOutput: IntRange, noInput: IntRange) : this(slots, noOutput.toList().toTypedArray(), noInput.toList().toTypedArray())

    val inputSlots: List<ItemStack>
        get() = stacks.filterIndexed { index, _ -> index !in noInputSlots }

    val outputSlots: List<ItemStack>
        get() = stacks.filterIndexed { index, _ -> index !in noOutputSlots }

    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean) = if (!isItemValid(slot, stack)) stack else super.insertItem(slot, stack, simulate)

    override fun isItemValid(slot: Int, itemStack: ItemStack) = slot !in noInputSlots

    override fun extractItem(slot: Int, amount: Int, simulate: Boolean) = if (slot in noOutputSlots) ItemStack.EMPTY else super.extractItem(slot, amount, simulate)

    operator fun get(slot: Int) = stacks[slot]
}