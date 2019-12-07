package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.gui.slots.PlayerSlot
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import net.minecraft.entity.player.PlayerInventory
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class PlayerSlotComponent(val slotIndex: Int, override var x: Int, override var y: Int) : IGUIComponent {
    val texture = BaseTextures.ITEM_SLOT
    override var width = 18
    override var height = 18

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) =
            texture.render(xOffset + x, yOffset + y, width, height)

    override fun isSelected(mouseX: Double, mouseY: Double) = x + 1 < mouseX && mouseX < x + width - 2 && y + 1 < mouseY && mouseY < y + height - 2

    override fun addSlots(playerInv: PlayerInventory, xOffset: Int, yOffset: Int) = listOf(PlayerSlot(playerInv, slotIndex, xOffset + (width - 16) / 2 + x, yOffset + (height - 16) / 2 + y))
}