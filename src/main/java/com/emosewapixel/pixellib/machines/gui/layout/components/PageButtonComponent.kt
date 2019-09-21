package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import net.minecraft.util.ResourceLocation

class PageButtonComponent(val texture: ResourceLocation, val page: Int, override val x: Int, override val y: Int) : IGUIComponent {
    override val tooltips = mutableListOf<String>()
    var width = 16
    var height = 16

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        screen.minecraft.textureManager.bindTexture(texture)
        screen.blit(screen.guiLeft + x, screen.guiTop + y, 0, 0, width, height)
    }

    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        screen.container.te.guiLayout.current = screen.container.te.guiLayout[page]
        return true
    }
}