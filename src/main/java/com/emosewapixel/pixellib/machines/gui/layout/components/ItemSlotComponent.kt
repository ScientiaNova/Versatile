package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.layout.DefaultSizeConstants
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.gui.slots.ItemHandlerSlot
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import net.minecraft.entity.player.PlayerInventory
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.items.IItemHandlerModifiable

open class ItemSlotComponent(val property: IValueProperty<IItemHandlerModifiable>, override var x: Int, override var y: Int) : IGUIComponent {
    var texture = BaseTextures.ITEM_SLOT
    override var width = DefaultSizeConstants.SLOT_WIDTH
    override var height = DefaultSizeConstants.SLOT_HEIGHT
    var slotIndex = 0

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) =
            texture.render(xOffset + x, yOffset + y, width, height)

    override fun isSelected(mouseX: Double, mouseY: Double) = x + 1 < mouseX && mouseX < x + width - 2 && y + 1 < mouseY && mouseY < y + height - 2

    override fun addSlots(playerInv: PlayerInventory, xOffset: Int, yOffset: Int) = listOf(ItemHandlerSlot(property.value, slotIndex, xOffset + x + (width - 16) / 2, yOffset + y + (height - 16) / 2))

    override fun addProperties() = setOf(property)
}