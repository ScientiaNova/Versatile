package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.layout.IPropertyGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.GUITexture
import com.emosewapixel.pixellib.machines.properties.implementations.UpdatePageProperty
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class PageButtonComponent(override val property: UpdatePageProperty, val page: Int, val texture: GUITexture, override var x: Int, override var y: Int) : IPropertyGUIComponent {
    override var width = 16
    override var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int) = texture.render(xOffset + x, yOffset + y, width, height)

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int): Boolean {
        property.pageId = page
        return true
    }
}