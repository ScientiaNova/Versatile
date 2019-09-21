package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import net.minecraft.util.ResourceLocation

class ImageComponent(val texture: ResourceLocation) : IGUIComponent {
    override val tooltips = mutableListOf<String>()
    override var x = 0
    override var y = 0
    var width = 176
    var height = 166

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        screen.minecraft.textureManager.bindTexture(texture)
        screen.blit(screen.guiLeft + x, screen.guiTop + y, 0, 0, width, height)
    }

    override fun isSelected(mouseX: Int, mouseY: Int) = false
}