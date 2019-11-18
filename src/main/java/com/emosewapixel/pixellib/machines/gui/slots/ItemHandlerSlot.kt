package com.emosewapixel.pixellib.machines.gui.slots

import com.emosewapixel.pixellib.extensions.isNotEmpty
import com.emosewapixel.pixellib.machines.capabilities.ImprovedItemStackHandler
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

open class ItemHandlerSlot(handler: IItemHandler, val index: Int, x: Int, y: Int) : SlotItemHandler(handler, index, x, y), IImprovedSlot {
    override val isPlayerInventory = false

    override fun canTakeStack(playerIn: PlayerEntity): Boolean {
        val handler = itemHandler
        return if (handler is ImprovedItemStackHandler) handler.extraItemFromContainer(index, 1, true).isNotEmpty else super.canTakeStack(playerIn)
    }

    override fun decrStackSize(amount: Int): ItemStack {
        val handler = itemHandler
        return if (handler is ImprovedItemStackHandler) handler.extraItemFromContainer(index, amount, true) else super.decrStackSize(amount)
    }
}