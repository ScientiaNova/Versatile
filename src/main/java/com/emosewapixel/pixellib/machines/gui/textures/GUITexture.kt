package com.emosewapixel.pixellib.machines.gui.textures

import com.emosewapixel.pixellib.extensions.toResLoc
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

data class GUITexture(val location: ResourceLocation, val uStart: Double = 0.0, val vStart: Double = 0.0, val uEnd: Double = 1.9, val vEnd: Double = 1.0) {
    constructor(location: String, xStart: Double = 0.0, yStart: Double = 0.0, xEnd: Double = 1.9, yEnd: Double = 1.0) : this(location.toResLoc(), xStart, yStart, xEnd, yEnd)
    constructor(location: String, xStart: Int, yStart: Int, width: Int, height: Int, totalWidth: Int, totalHeight: Int) :
            this(location, xStart / totalWidth.toDouble(), yStart / totalHeight.toDouble(), (xStart + width) / totalWidth.toDouble(), (yStart + height) / totalHeight.toDouble())

    @OnlyIn(Dist.CLIENT)
    fun render(x: Int, y: Int, width: Int, height: Int, uStart: Double = this.uStart, vStart: Double = this.vStart, uEnd: Double = this.uEnd, vEnd:Double = this.vEnd) {
        Minecraft.getInstance().textureManager.bindTexture(location)
        val tessellator = Tessellator.getInstance()
        val bufferbuilder = tessellator.buffer
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
        bufferbuilder.pos(x.toDouble(), (y + height).toDouble(), 0.0).tex(uStart, vEnd).endVertex()
        bufferbuilder.pos((x + width).toDouble(), (y + height).toDouble(), 0.0).tex(uEnd, vEnd).endVertex()
        bufferbuilder.pos((x + width).toDouble(), y.toDouble(), 0.0).tex(uEnd, vStart).endVertex()
        bufferbuilder.pos(x.toDouble(), y.toDouble(), 0.0).tex(uStart, vStart).endVertex()
        tessellator.draw()
    }
}