package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import net.minecraft.util.ResourceLocation

class ItemComponent(property: String, x: Int, y: Int, texture: ResourceLocation) : AbstractItemSlotComponent(property, x, y, texture) {
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}