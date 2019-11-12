package com.emosewapixel.pixellib.machines.gui

import com.emosewapixel.pixellib.extensions.alphaF
import com.emosewapixel.pixellib.extensions.blueF
import com.emosewapixel.pixellib.extensions.greenF
import com.emosewapixel.pixellib.extensions.redF
import com.mojang.blaze3d.platform.GlStateManager
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fml.client.config.GuiUtils

@OnlyIn(Dist.CLIENT)
object GUiUtils {
    val mc = Minecraft.getInstance()

    @JvmStatic
    fun drawItemStack(stack: ItemStack, x: Int, y: Int) {
        val itemRenderer = mc.itemRenderer
        GlStateManager.translatef(0.0f, 0.0f, 32.0f)
        itemRenderer.zLevel = 200.0f
        val font = stack.item.getFontRenderer(stack) ?: Minecraft.getInstance().fontRenderer
        itemRenderer.renderItemAndEffectIntoGUI(stack, x, y)
        itemRenderer.renderItemOverlayIntoGUI(font, stack, x, y, null)
        itemRenderer.zLevel = 0.0f
    }

    @JvmStatic
    fun drawFluidStack(stack: FluidStack, x: Int, y: Int, width: Int, height: Int) {
        val sprite = mc.textureMap.getSprite(stack.fluid.attributes.stillTexture)
        GlStateManager.enableBlend()
        val color = stack.fluid.attributes.color
        GlStateManager.color4f(color.redF, color.greenF, color.blueF, color.alphaF)
        ContainerScreen.blit(x + 1, y + 1, 0, width - 2, height - 2, sprite)
        GlStateManager.disableBlend()
        if (stack.amount / 1000 > 0) {
            val s = (stack.amount / 1000).toString()
            val fr = mc.fontRenderer
            fr.drawStringWithShadow(s, x + width - 1f - fr.getStringWidth(s), y + height - 9f, 16777215)
        }
    }

    @JvmStatic
    fun renderTooltip(tooltips: List<String>, x: Int, y: Int) = GuiUtils.drawHoveringText(tooltips, x, y, mc.mainWindow.scaledWidth, mc.mainWindow.scaledHeight, -1, mc.fontRenderer)

    @JvmStatic
    fun renderTooltip(stack: ItemStack, x: Int, y: Int) {
        GuiUtils.preItemToolTip(stack)
        this.renderTooltip(this.getTooltipsFromItem(stack), x, y)
        GuiUtils.postItemToolTip()
    }

    @JvmStatic
    fun getTooltipsFromItem(stack: ItemStack) = stack.getTooltip(mc.player, if (mc.gameSettings.advancedItemTooltips) ITooltipFlag.TooltipFlags.ADVANCED else ITooltipFlag.TooltipFlags.NORMAL).map(ITextComponent::getFormattedText)
}