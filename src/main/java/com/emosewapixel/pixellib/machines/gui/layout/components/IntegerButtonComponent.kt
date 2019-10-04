package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateIntPacket
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.renderer.texture.TextureAtlasSprite

class IntegerButtonComponent(val property: String, val textures: List<TextureAtlasSprite>, override val x: Int, override val y: Int, val width: Int, val height: Int) : IGUIComponent {
    override val tooltips = mutableListOf<String>()

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        val value = screen.container.te.properties[property] as? Int ?: 0
        AbstractGui.blit(screen.guiLeft + x, screen.guiTop + y, screen.blitOffset, width, height, textures[value])

    }

    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        val value = (screen.container.te.properties[property] as? Int) ?: 0
        val newValue = if (value + 1 < textures.size) value + 1 else 0
        screen.container.clientProperties[property] = newValue
        NetworkHandler.CHANNEL.sendToServer(UpdateIntPacket(screen.container.te.pos, property, newValue))
        return true
    }
}