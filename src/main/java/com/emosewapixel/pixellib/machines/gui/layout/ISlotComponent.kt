package com.emosewapixel.pixellib.machines.gui.layout

import com.emosewapixel.pixellib.machines.gui.slots.IImprovedSlot
import net.minecraft.entity.player.PlayerInventory

interface ISlotComponent : IGUIComponent {
    fun setupSlot(playerInv: PlayerInventory): IImprovedSlot
}