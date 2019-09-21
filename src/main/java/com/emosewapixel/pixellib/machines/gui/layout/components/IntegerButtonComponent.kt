package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import net.minecraft.util.ResourceLocation

class IntegerButtonComponent(val property: String, val textures: List<ResourceLocation>, override val x: Int, override val y: Int, val width: Int, val height: Int) : IGUIComponent {
    override val tooltips = mutableListOf<String>()

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        val value = screen.container.clientProperties[property] as? Int ?: 0
        screen.minecraft.textureManager.bindTexture(textures[value])
        screen.blit(screen.guiLeft + x, screen.guiTop + y, 0, 0, width, height)
    }

    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        val properties = screen.container.clientProperties
        val value = (properties[property] as? Int) ?: 0
        properties[property] = if (value + 1 < textures.size) value + 1 else 0
        return true
    }
}