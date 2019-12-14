package com.emosewapixel.pixellib.machines.gui.layout.components.buttons

import com.emosewapixel.pixellib.machines.gui.layout.components.ITexturedGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.still.GUITexture
import com.emosewapixel.pixellib.machines.properties.implementations.TEPageProperty
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class PageButtonComponent(val property: TEPageProperty, val pageId: Int, override val texture: GUITexture, override var x: Int, override var y: Int) : ITexturedGUIComponent {
    override var width = 16
    override var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) =
            texture.draw(xOffset + x, yOffset + y, width, height)

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int): Boolean {
        if (!isSelected(mouseX - xOffset, mouseY - yOffset)) return false
        property.page = pageId
        return true
    }

    override fun addProperties() = setOf(property)
}