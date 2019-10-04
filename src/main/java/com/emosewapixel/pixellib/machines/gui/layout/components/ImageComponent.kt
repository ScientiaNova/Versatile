package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.renderer.texture.TextureAtlasSprite

class ImageComponent(val texture: TextureAtlasSprite) : IGUIComponent {
    override val tooltips = mutableListOf<String>()
    override var x = 0
    override var y = 0
    var width = 176
    var height = 166

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) =
            AbstractGui.blit(screen.guiLeft + x, screen.guiTop + y, screen.blitOffset, width, height, texture)

    override fun isSelected(mouseX: Int, mouseY: Int) = false
}