package com.scientianovateam.versatile.machines.gui.slots

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.container.ClickType
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack

sealed class SlotLogic {
    object Vanilla : SlotLogic()
    data class Custom(val func: (Slot, Int, ClickType, PlayerEntity) -> ItemStack) : SlotLogic()
}