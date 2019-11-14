package com.emosewapixel.pixellib.integration.jei

import com.mojang.blaze3d.platform.GlStateManager
import mezz.jei.plugins.vanilla.ingredients.fluid.FluidStackRenderer
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.AtlasTexture
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.fluid.Fluid
import net.minecraftforge.fluids.FluidStack

class GUIFluidRenderer : FluidStackRenderer(1000, false, 16, 16, null) {
    override fun render(xPosition: Int, yPosition: Int, fluidStack: FluidStack?) {
        GlStateManager.enableBlend()
        GlStateManager.enableAlphaTest()

        drawFluid(xPosition, yPosition, fluidStack)

        GlStateManager.color4f(1f, 1f, 1f, 1f)

        GlStateManager.disableAlphaTest()
        GlStateManager.disableBlend()
    }

    private fun drawFluid(xPosition: Int, yPosition: Int, fluidStack: FluidStack?) {
        if (fluidStack == null) return
        val fluid = fluidStack.fluid ?: return

        val fluidStillSprite = getStillFluidSprite(fluid)

        val fluidColor = fluid.attributes.color

        drawTiledSprite(xPosition, yPosition, fluidColor, fluidStillSprite)
    }

    private fun drawTiledSprite(xPosition: Int, yPosition: Int, color: Int, sprite: TextureAtlasSprite) {
        val minecraft = Minecraft.getInstance()
        minecraft.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE)
        setGLColorFromInt(color)
        drawTextureWithMasking(xPosition.toDouble(), yPosition.toDouble(), sprite, 0, 0, 100.0)
    }

    private fun getStillFluidSprite(fluid: Fluid): TextureAtlasSprite {
        val minecraft = Minecraft.getInstance()
        val textureMapBlocks = minecraft.textureMap
        val fluidStill = fluid.attributes.stillTexture
        return textureMapBlocks.getSprite(fluidStill)
    }

    private fun setGLColorFromInt(color: Int) {
        val red = (color shr 16 and 0xFF) / 255.0f
        val green = (color shr 8 and 0xFF) / 255.0f
        val blue = (color and 0xFF) / 255.0f

        GlStateManager.color4f(red, green, blue, 1.0f)
    }

    private fun drawTextureWithMasking(xCoord: Double, yCoord: Double, textureSprite: TextureAtlasSprite, maskTop: Int, maskRight: Int, zLevel: Double) {
        val uMin = textureSprite.minU.toDouble()
        var uMax = textureSprite.maxU.toDouble()
        val vMin = textureSprite.minV.toDouble()
        var vMax = textureSprite.maxV.toDouble()
        uMax -= maskRight / 16.0 * (uMax - uMin)
        vMax -= maskTop / 16.0 * (vMax - vMin)

        val tessellator = Tessellator.getInstance()
        val bufferBuilder = tessellator.buffer
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
        bufferBuilder.pos(xCoord, yCoord + 16, zLevel).tex(uMin, vMax).endVertex()
        bufferBuilder.pos(xCoord + 16 - maskRight, yCoord + 16, zLevel).tex(uMax, vMax).endVertex()
        bufferBuilder.pos(xCoord + 16 - maskRight, yCoord + maskTop, zLevel).tex(uMax, vMin).endVertex()
        bufferBuilder.pos(xCoord, yCoord + maskTop, zLevel).tex(uMin, vMin).endVertex()
        tessellator.draw()
    }
}