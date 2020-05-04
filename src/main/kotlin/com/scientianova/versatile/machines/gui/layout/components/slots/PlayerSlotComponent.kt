package com.scientianova.versatile.machines.gui.layout.components.slots

import com.scientianovateam.versatile.machines.gui.layout.components.ITexturedGUIComponent
import com.scientianovateam.versatile.machines.gui.slots.PlayerSlot
import com.scientianovateam.versatile.machines.gui.textures.BaseTextures
import net.minecraft.entity.player.PlayerInventory

open class PlayerSlotComponent(val slotIndex: Int, override var x: Int, override var y: Int) : ITexturedGUIComponent {
    override val texture = BaseTextures.ITEM_SLOT
    override var width = 18
    override var height = 18

    override fun isSelected(mouseX: Double, mouseY: Double) = x + 1 < mouseX && mouseX < x + width - 2 && y + 1 < mouseY && mouseY < y + height - 2

    override fun addSlots(playerInv: PlayerInventory, xOffset: Int, yOffset: Int) = listOf(PlayerSlot(playerInv, slotIndex, xOffset + (width - 16) / 2 + x, yOffset + (height - 16) / 2 + y))
}