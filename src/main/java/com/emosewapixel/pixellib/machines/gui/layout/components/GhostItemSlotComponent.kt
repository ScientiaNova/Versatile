package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import net.minecraft.client.renderer.texture.TextureAtlasSprite

class GhostItemSlotComponent(property: String, x: Int, y: Int, texture: TextureAtlasSprite) : AbstractItemSlotComponent(property, x, y, texture) {
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}