package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.layout.ISlotComponent
import com.emosewapixel.pixellib.machines.gui.slots.PlayerSlot
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import net.minecraft.entity.player.PlayerInventory
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class PlayerSlotComponent(val slotIndex: Int, override val x: Int, override val y: Int) : ISlotComponent {
    val texture = BaseTextures.ITEM_SLOT
    override val tooltips = mutableListOf<String>()
    override var width = 18
    override var height = 18

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int) = texture.render(xOffset + x, yOffset + y, width, height)

    override fun setupSlot(playerInv: PlayerInventory) = PlayerSlot(playerInv, slotIndex, (width - 16) / 2 + x, (height - 16) / 2 + y)
}