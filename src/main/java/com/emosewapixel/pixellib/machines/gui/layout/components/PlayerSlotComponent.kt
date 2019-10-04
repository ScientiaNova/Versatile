package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.ISlotComponent
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.renderer.texture.TextureAtlasSprite

class PlayerSlotComponent(val slotIndex: Int, override val x: Int, override val y: Int, override val texture: TextureAtlasSprite) : ISlotComponent {
    override val tooltips = mutableListOf<String>()
    override var width = 18
    override var height = 18

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        AbstractGui.blit(screen.guiLeft + x, screen.guiTop + y, screen.blitOffset, width, height, texture)
        val stack = screen.container.playerInv.getStackInSlot(slotIndex)
        if (!stack.isEmpty)
            screen.drawItemStack(stack, screen.guiLeft + x, screen.guiTop + y)
    }

    override fun drawInForeground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        if (isSelected(mouseX - screen.guiLeft, mouseY - screen.guiTop)) {
            AbstractGui.fill(screen.guiLeft + x + 1, screen.guiTop + y + 1, width - 2, height - 2, 0xFFFFF)
            val stack = screen.container.playerInv.getStackInSlot(slotIndex)
            if (!stack.isEmpty)
                screen.renderTooltip(stack, mouseX, mouseY)
        }
    }
}