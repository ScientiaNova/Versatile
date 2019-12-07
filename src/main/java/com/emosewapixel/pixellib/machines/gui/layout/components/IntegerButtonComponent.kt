package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.AnimatedGUITexture
import com.emosewapixel.pixellib.machines.properties.ILimitedIntegerProperty
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class IntegerButtonComponent(val property: ILimitedIntegerProperty, val textures: AnimatedGUITexture, override var x: Int, override var y: Int) : IGUIComponent {
    override var width = 16
    override var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) =
            textures.render(xOffset + x, yOffset + y, width, height, frame = property.value)

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int): Boolean {
        if (!isSelected(mouseX - xOffset, mouseY - yOffset)) return false
        property.inc()
        return true
    }

    override fun addProperties() = setOf(property)
}