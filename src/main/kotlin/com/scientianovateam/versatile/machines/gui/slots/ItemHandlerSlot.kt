package com.scientianovateam.versatile.machines.gui.slots

import com.scientianovateam.versatile.common.extensions.isNotEmpty
import com.scientianovateam.versatile.machines.capabilities.items.IContainerItemHandler
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

open class ItemHandlerSlot(handler: IItemHandler, val index: Int, x: Int, y: Int) : SlotItemHandler(handler, index, x, y), IImprovedSlot {
    override val isPlayerInventory = false

    override fun canTakeStack(playerIn: PlayerEntity): Boolean {
        val handler = itemHandler
        return if (handler is IContainerItemHandler) handler.forceExtractItem(index, 1, true).isNotEmpty else super.canTakeStack(playerIn)
    }

    override fun decrStackSize(amount: Int): ItemStack {
        val handler = itemHandler
        return if (handler is IContainerItemHandler) handler.forceExtractItem(index, amount, false) else super.decrStackSize(amount)
    }
}