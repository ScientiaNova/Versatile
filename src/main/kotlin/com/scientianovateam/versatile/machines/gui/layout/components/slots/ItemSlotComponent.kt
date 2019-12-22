package com.scientianovateam.versatile.machines.gui.layout.components.slots

import com.scientianovateam.versatile.machines.gui.layout.DefaultSizeConstants
import com.scientianovateam.versatile.machines.gui.layout.components.ITexturedGUIComponent
import com.scientianovateam.versatile.machines.gui.slots.ItemHandlerSlot
import com.scientianovateam.versatile.machines.gui.textures.BaseTextures
import com.scientianovateam.versatile.machines.properties.IValueProperty
import net.minecraft.entity.player.PlayerInventory
import net.minecraftforge.items.IItemHandlerModifiable

open class ItemSlotComponent(val property: IValueProperty<out IItemHandlerModifiable>, val slotIndex: Int) : ITexturedGUIComponent {
    override var texture = BaseTextures.ITEM_SLOT
    override var x = 0
    override var y = 0
    override var width = DefaultSizeConstants.SLOT_WIDTH
    override var height = DefaultSizeConstants.SLOT_HEIGHT

    override fun isSelected(mouseX: Double, mouseY: Double) = x + 1 < mouseX && mouseX < x + width - 2 && y + 1 < mouseY && mouseY < y + height - 2

    override fun addSlots(playerInv: PlayerInventory, xOffset: Int, yOffset: Int) = listOf(ItemHandlerSlot(property.value, slotIndex, xOffset + x + (width - 16) / 2, yOffset + y + (height - 16) / 2))

    override fun addProperties() = setOf(property)
}