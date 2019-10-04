package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.ResourceLocation

class ProgressBarComponent(val backGroundTex: TextureAtlasSprite, val fillTexture: ResourceLocation, val current: BaseScreen.() -> Int, val max: BaseScreen.() -> Int) : IGUIComponent {
    override val tooltips = mutableListOf<String>()
    var direction = Direction2D.RIGHT
    override var x = 79
    override var y = 34
    var width = 24
    var height = 16

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        AbstractGui.blit(screen.guiLeft + x, screen.guiTop + y, screen.blitOffset, width, height, backGroundTex)
        screen.minecraft.textureManager.bindTexture(fillTexture)
        val current = (max(screen).toDouble() / current(screen) * when (direction) {
            Direction2D.RIGHT, Direction2D.LEFT -> width
            Direction2D.UP, Direction2D.DOWN -> height
        }).toInt()
        when (direction) {
            Direction2D.UP -> screen.blit(screen.guiLeft + x, screen.guiTop + y + height - current, 0, 0, width, current)
            Direction2D.DOWN -> screen.blit(screen.guiLeft + x, screen.guiTop + y, 0, 0, width, current)
            Direction2D.LEFT -> screen.blit(screen.guiLeft + x + width - current, screen.guiTop + y, 0, 0, current, height)
            Direction2D.RIGHT -> screen.blit(screen.guiLeft + x, screen.guiTop + y, 0, 0, current, height)
        }
    }

    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height
}