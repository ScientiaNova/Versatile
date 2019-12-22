package com.scientianovateam.versatile.machines.gui.slots

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Slot

open class PlayerSlot(inventory: PlayerInventory, index: Int, x: Int, y: Int) : Slot(inventory, index, x, y), IImprovedSlot {
    override val isPlayerInventory = true
}