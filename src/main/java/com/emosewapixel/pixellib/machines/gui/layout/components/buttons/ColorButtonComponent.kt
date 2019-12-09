package com.emosewapixel.pixellib.machines.gui.layout.components.buttons

import com.emosewapixel.pixellib.extensions.alphaF
import com.emosewapixel.pixellib.extensions.blueF
import com.emosewapixel.pixellib.extensions.greenF
import com.emosewapixel.pixellib.extensions.redF
import com.emosewapixel.pixellib.machines.gui.layout.components.ITexturedGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.GUITexture
import com.emosewapixel.pixellib.machines.properties.ILimitedIntegerProperty
import com.mojang.blaze3d.platform.GlStateManager
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class ColorButtonComponent(val property: ILimitedIntegerProperty, override val texture: GUITexture, val colors: IntArray, override var x: Int, override var y: Int) : ITexturedGUIComponent {
    override var width = 16
    override var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        GlStateManager.enableBlend()
        val color = colors.getOrElse(property.value) { colors[0] }
        GlStateManager.color4f(color.redF, color.greenF, color.blueF, color.alphaF)
        texture.draw(xOffset + x, yOffset + y, width, height)
        GlStateManager.disableBlend()
    }

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int): Boolean {
        if (!isSelected(mouseX - xOffset, mouseY - yOffset)) return false
        property.inc()
        return true
    }

    override fun addProperties() = setOf(property)
}