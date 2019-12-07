package com.emosewapixel.pixellib.machines.capabilities

import net.minecraft.item.ItemStack
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.IItemHandlerModifiable

open class ItemCapabilityWrapper : IItemHandlerModifiable, ICapabilityProvider {
    protected val handlers = arrayListOf<IItemHandler>()

    fun addHandler(handler: IItemHandler) = handlers.add(handler)

    val handlerSlotCounts get() = handlers.map(IItemHandler::getSlots)

    override fun getSlots() = handlerSlotCounts.sum()

    fun getSlotAndHandlerFromIndex(index: Int): Pair<Int, Int> {
        var indexInCurrent = index
        handlers.forEachIndexed { i, handler -> if (handler.slots > indexInCurrent) return i to indexInCurrent else indexInCurrent -= handler.slots }
        throw IndexOutOfBoundsException()
    }

    override fun getSlotLimit(slot: Int): Int {
        val (handlerIndex, index) = getSlotAndHandlerFromIndex(slot)
        return handlers[handlerIndex].getSlotLimit(index)
    }

    override fun getStackInSlot(slot: Int): ItemStack {
        val (handlerIndex, index) = getSlotAndHandlerFromIndex(slot)
        return handlers[handlerIndex].getStackInSlot(index)
    }

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
        val (handlerIndex, index) = getSlotAndHandlerFromIndex(slot)
        return handlers[handlerIndex].isItemValid(index, stack)
    }

    override fun setStackInSlot(slot: Int, stack: ItemStack) {
        val (handlerIndex, index) = getSlotAndHandlerFromIndex(slot)
        (handlers[handlerIndex] as? IItemHandlerModifiable)?.setStackInSlot(index, stack)
    }

    override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
        val (handlerIndex, index) = getSlotAndHandlerFromIndex(slot)
        return handlers[handlerIndex].extractItem(index, amount, simulate)
    }

    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
        val (handlerIndex, index) = getSlotAndHandlerFromIndex(slot)
        return handlers[handlerIndex].insertItem(index, stack, simulate)
    }

    private val optional = LazyOptional.of { this }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> =
            if (cap === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) optional.cast() else LazyOptional.empty()
}