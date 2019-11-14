package com.emosewapixel.pixellib.integration.jei

import com.emosewapixel.pixellib.extensions.blueF
import com.emosewapixel.pixellib.extensions.greenF
import com.emosewapixel.pixellib.extensions.redF
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
        drawTextureWithMasking(xPosition.toDouble(), yPosition.toDouble(), sprite)
    }

    private fun getStillFluidSprite(fluid: Fluid): TextureAtlasSprite {
        val minecraft = Minecraft.getInstance()
        val textureMapBlocks = minecraft.textureMap
        val fluidStill = fluid.attributes.stillTexture
        return textureMapBlocks.getSprite(fluidStill)
    }

    private fun setGLColorFromInt(color: Int) = GlStateManager.color4f(color.redF, color.greenF, color.blueF, 1.0f)

    private fun drawTextureWithMasking(xCoord: Double, yCoord: Double, textureSprite: TextureAtlasSprite) {
        val uMin = textureSprite.minU.toDouble()
        val uMax = textureSprite.maxU.toDouble()
        val vMin = textureSprite.minV.toDouble()
        val vMax = textureSprite.maxV.toDouble()
        val zLevel = 100.0

        val tessellator = Tessellator.getInstance()
        val bufferBuilder = tessellator.buffer
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
        bufferBuilder.pos(xCoord, yCoord + 16, zLevel).tex(uMin, vMax).endVertex()
        bufferBuilder.pos(xCoord + 16, yCoord + 16, zLevel).tex(uMax, vMax).endVertex()
        bufferBuilder.pos(xCoord + 16, yCoord, zLevel).tex(uMax, vMin).endVertex()
        bufferBuilder.pos(xCoord, yCoord, zLevel).tex(uMin, vMin).endVertex()
        tessellator.draw()
    }
}