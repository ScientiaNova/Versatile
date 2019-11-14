package com.emosewapixel.pixellib.machines.gui.slots

import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

open class ItemHandlerSlot(handler: IItemHandler, index: Int, x: Int, y: Int) : SlotItemHandler(handler, index, x, y), IImprovedSlot {
    override val isPlayerInventory = false
}