package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.extensions.alphaF
import com.emosewapixel.pixellib.extensions.blueF
import com.emosewapixel.pixellib.extensions.greenF
import com.emosewapixel.pixellib.extensions.redF
import com.emosewapixel.pixellib.machines.gui.layout.IPropertyGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.GUITexture
import com.emosewapixel.pixellib.machines.properties.ILimitedIntegerProperty
import com.mojang.blaze3d.platform.GlStateManager
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class ColorButtonComponent(override val property: ILimitedIntegerProperty, val texture: GUITexture, val colors: IntArray, override val x: Int, override val y: Int) : IPropertyGUIComponent {
    override val tooltips = mutableListOf<String>()
    override var width = 16
    override var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, xOffset: Int, yOffset: Int) {
        GlStateManager.enableBlend()
        val color = colors.getOrElse(property.value) { colors[0] }
        GlStateManager.color4f(color.redF, color.greenF, color.blueF, color.alphaF)
        texture.render(xOffset + x, yOffset + y, width, height)
        GlStateManager.disableBlend()
    }

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int): Boolean {
        property.inc()
        return true
    }
}