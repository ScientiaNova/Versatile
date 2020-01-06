package com.scientianovateam.versatile.machines.gui.layout.components.buttons

import com.scientianovateam.versatile.common.extensions.alphaF
import com.scientianovateam.versatile.common.extensions.blueF
import com.scientianovateam.versatile.common.extensions.greenF
import com.scientianovateam.versatile.common.extensions.redF
import com.scientianovateam.versatile.machines.gui.layout.components.ITexturedGUIComponent
import com.scientianovateam.versatile.machines.gui.textures.still.GUITexture
import com.scientianovateam.versatile.machines.properties.ILimitedIntegerProperty
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.renderer.RenderHelper
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class ColorButtonComponent(val property: ILimitedIntegerProperty, override val texture: GUITexture, val colors: IntArray, override var x: Int, override var y: Int) : ITexturedGUIComponent {
    override var width = 16
    override var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        RenderSystem.enableBlend()
        val color = colors.getOrElse(property.value) { colors[0] }
        RenderSystem.color4f(color.redF, color.greenF, color.blueF, color.alphaF)
        texture.draw(xOffset + x, yOffset + y, width, height)
        RenderSystem.disableBlend()
    }

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int): Boolean {
        if (!isSelected(mouseX - xOffset, mouseY - yOffset)) return false
        property.inc()
        return true
    }

    override fun addProperties() = setOf(property)
}