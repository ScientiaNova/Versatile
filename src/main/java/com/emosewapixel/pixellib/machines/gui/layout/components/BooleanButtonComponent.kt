package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import net.minecraft.util.ResourceLocation

class BooleanButtonComponent(val property: String, val onTex: ResourceLocation, val offTex: ResourceLocation, override val x: Int, override val y: Int) : IGUIComponent {
    override val tooltips = mutableListOf<String>()
    var width = 16
    var height = 16

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        val bool = screen.container.clientProperties[property] as? Boolean ?: false
        screen.minecraft.textureManager.bindTexture(if (bool) onTex else offTex)
        screen.blit(screen.guiLeft + x, screen.guiTop + y, 0, 0, width, height)
    }

    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        val properties = screen.container.clientProperties
        properties[property] = (properties[property] as? Boolean)?.not() ?: false
        return true
    }
}