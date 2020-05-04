package com.scientianova.versatile.machines.gui

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import com.scientianovateam.versatile.common.extensions.alphaF
import com.scientianovateam.versatile.common.extensions.blueF
import com.scientianovateam.versatile.common.extensions.greenF
import com.scientianovateam.versatile.common.extensions.redF
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.WorldVertexBufferUploader
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.inventory.container.PlayerContainer
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fml.client.config.GuiUtils

@OnlyIn(Dist.CLIENT)
object GUiUtils {
    private val mc = Minecraft.getInstance()

    @JvmStatic
    fun drawItemStack(stack: ItemStack, x: Int, y: Int, z: Int = 200, altText: String? = null) {
        val itemRenderer = mc.itemRenderer
        itemRenderer.zLevel = z.toFloat()
        val font = stack.item.getFontRenderer(stack) ?: mc.fontRenderer
        itemRenderer.renderItemAndEffectIntoGUI(stack, x, y)
        itemRenderer.renderItemOverlayIntoGUI(font, stack, x, y, altText)
        itemRenderer.zLevel = 0f
    }

    @JvmStatic
    fun drawTransparentItemStack(stack: ItemStack, x: Int, y: Int, z: Int = 200) {
        val itemRenderer = mc.itemRenderer
        itemRenderer.zLevel = z.toFloat()
        itemRenderer.renderItemAndEffectIntoGUI(stack, x, y)
        itemRenderer.zLevel = 0f
        RenderSystem.colorMask(true, true, true, true)
        drawColoredRectangle(0x7f7f7f80, x, y, 16, 16)
    }

    @JvmStatic
    fun drawFluidStack(stack: FluidStack, x: Int, y: Int, width: Int, height: Int) {
        RenderSystem.enableBlend()
        RenderSystem.enableAlphaTest()

        val texture = mc.getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(stack.fluid.attributes.stillTexture)
        val color = stack.fluid.attributes.color
        RenderSystem.color4f(color.redF, color.greenF, color.blueF, 1f)

        drawTexture(texture, x, y, width, height, z = 100)

        RenderSystem.color4f(1f, 1f, 1f, 1f)

        RenderSystem.disableAlphaTest()
        RenderSystem.disableBlend()

        if (stack.amount / 1000 > 0) {
            val string = (stack.amount / 1000).toString()
            val fontRenderer = mc.fontRenderer
            RenderSystem.disableDepthTest()
            fontRenderer.drawStringWithShadow(string, x + width + 1f - fontRenderer.getStringWidth(string), y + height - 7f, 16777215)
            RenderSystem.enableDepthTest()
        }
    }

    @JvmStatic
    fun drawTransparentFluidStack(stack: FluidStack, x: Int, y: Int, width: Int, height: Int) {
        RenderSystem.enableBlend()
        RenderSystem.enableAlphaTest()

        val texture = mc.getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(stack.fluid.attributes.stillTexture)
        val color = stack.fluid.attributes.color
        RenderSystem.color4f(color.redF, color.greenF, color.blueF, 1f)

        drawTexture(texture, x, y, width, height, z = 100)
        RenderSystem.colorMask(true, true, true, false)
        drawColoredRectangle(0x7f434343, x, y, 16, 16)

        RenderSystem.color4f(1f, 1f, 1f, 1f)

        RenderSystem.disableAlphaTest()
        RenderSystem.disableBlend()
    }

    @JvmStatic
    fun drawTooltip(tooltips: List<String>, x: Double, y: Double) = GuiUtils.drawHoveringText(tooltips, x.toInt(), y.toInt(), mc.mainWindow.scaledWidth, mc.mainWindow.scaledHeight, -1, mc.fontRenderer)

    @JvmStatic
    fun drawTooltip(stack: ItemStack, x: Double, y: Double) {
        GuiUtils.preItemToolTip(stack)
        drawTooltip(getTooltipsFromItem(stack), x, y)
        GuiUtils.postItemToolTip()
    }

    @JvmStatic
    fun drawTexture(sprite: TextureAtlasSprite, x: Int, y: Int, width: Int, height: Int, z: Int = 0) =
            drawTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE, x, y, width, height, sprite.minU, sprite.minV, sprite.maxU, sprite.maxV, z)

    @JvmStatic
    @JvmOverloads
    fun drawTexture(location: ResourceLocation, x: Int, y: Int, width: Int, height: Int, uStart: Float = 0f, vStart: Float = 0f, uEnd: Float = 1f, vEnd: Float = 1f, z: Int = 0) {
        mc.textureManager.bindTexture(location)
        val bufferBuilder = Tessellator.getInstance().buffer
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
        bufferBuilder.pos(x.toDouble(), (y + height).toDouble(), z.toDouble()).tex(uStart, vEnd).endVertex()
        bufferBuilder.pos((x + width).toDouble(), (y + height).toDouble(), z.toDouble()).tex(uEnd, vEnd).endVertex()
        bufferBuilder.pos((x + width).toDouble(), y.toDouble(), z.toDouble()).tex(uEnd, vStart).endVertex()
        bufferBuilder.pos(x.toDouble(), y.toDouble(), z.toDouble()).tex(uStart, vStart).endVertex()
        bufferBuilder.finishDrawing()
        WorldVertexBufferUploader.draw(bufferBuilder)
    }

    fun drawColoredRectangle(color: Int, x: Int, y: Int, width: Int, height: Int, z: Int = 0) {
        RenderSystem.disableTexture()
        RenderSystem.disableDepthTest()
        RenderSystem.enableBlend()
        RenderSystem.color4f(color.redF, color.greenF, color.blueF, color.alphaF)

        val bufferBuilder = Tessellator.getInstance().buffer
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO)

        bufferBuilder.begin(7, DefaultVertexFormats.POSITION)
        bufferBuilder.pos(x.toDouble(), (y + height).toDouble(), z.toDouble()).endVertex()
        bufferBuilder.pos((x + width).toDouble(), (y + height).toDouble(), z.toDouble()).endVertex()
        bufferBuilder.pos((x + width).toDouble(), y.toDouble(), z.toDouble()).endVertex()
        bufferBuilder.pos(x.toDouble(), y.toDouble(), z.toDouble()).endVertex()
        bufferBuilder.finishDrawing()
        WorldVertexBufferUploader.draw(bufferBuilder)

        RenderSystem.color4f(1f, 1f, 1f, 1f)
        RenderSystem.disableBlend()
        RenderSystem.enableDepthTest()
        RenderSystem.enableTexture()
    }

    @JvmStatic
    fun getTooltipsFromItem(stack: ItemStack) = stack.getTooltip(mc.player, if (mc.gameSettings.advancedItemTooltips) ITooltipFlag.TooltipFlags.ADVANCED else ITooltipFlag.TooltipFlags.NORMAL).map(ITextComponent::getFormattedText)
}