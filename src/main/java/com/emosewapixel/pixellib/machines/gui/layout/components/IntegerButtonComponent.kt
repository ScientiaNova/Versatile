package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.layout.IPropertyGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.AnimatedGUITexture
import com.emosewapixel.pixellib.machines.properties.ILimitedIntegerProperty
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class IntegerButtonComponent(override val property: ILimitedIntegerProperty, val textures: AnimatedGUITexture, override val x: Int, override val y: Int) : IPropertyGUIComponent {
    override val tooltips = mutableListOf<String>()
    override var width = 16
    override var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int) = textures.render(xOffset + x, yOffset + y, width, height, frame = property.value)

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int): Boolean {
        property.inc()
        return true
    }
}