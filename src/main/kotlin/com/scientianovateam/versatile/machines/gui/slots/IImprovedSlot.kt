package com.scientianovateam.versatile.machines.gui.slots

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack

interface IImprovedSlot {
    val isPlayerInventory get() = this is Slot && this.inventory is PlayerInventory

    fun onClick(): SlotLogic = SlotLogic.Vanilla

    fun merge(slots: List<Slot>): Boolean {
        var successful = false
        slots.filter(Slot::getHasStack).forEach {
            val stackInCurrent = it.stack
            if (getStack().item != stackInCurrent.item || getStack().tag != stackInCurrent.tag) return@forEach
            val mergeCount = (it.getItemStackLimit(getStack()).coerceAtMost(stackInCurrent.maxStackSize) - stackInCurrent.count).coerceAtMost(getStack().count)
            if (mergeCount == 0) return@forEach
            getStack().shrink(mergeCount)
            stackInCurrent.grow(mergeCount)
            successful = true
            if (getStack().isEmpty) return true
        }
        slots.filterNot(Slot::getHasStack).forEach {
            val mergeCount = it.getItemStackLimit(getStack()).coerceAtMost(getStack().count)
            if (mergeCount == 0) return@forEach
            it.putStack(getStack().split(mergeCount))
            successful = true
            if (getStack().isEmpty) return true
        }
        return successful
    }

    fun getStack(): ItemStack
}