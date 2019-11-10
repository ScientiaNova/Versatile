package com.emosewapixel.pixellib.machines.gui

import com.emosewapixel.pixellib.extensions.alphaF
import com.emosewapixel.pixellib.extensions.blueF
import com.emosewapixel.pixellib.extensions.greenF
import com.emosewapixel.pixellib.extensions.redF
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.OpenGUIPacket
import com.mojang.blaze3d.platform.GlStateManager
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.fluids.FluidStack

class BaseScreen(container: BaseContainer, playerInv: PlayerInventory, title: ITextComponent) : ContainerScreen<BaseContainer>(container, playerInv, title) {
    var blitOffset
        get() = super.blitOffset
        set(value) {
            super.blitOffset = value
        }

    val itemRenderer: ItemRenderer
        get() = super.itemRenderer

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) =
            container.guiPage.components.forEach { it.drawInBackground(mouseX, mouseY, this) }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) =
            container.guiPage.components.forEach { it.drawInForeground(mouseX, mouseY, this) }

    override fun mouseClicked(mouseX: Double, mouseY: Double, clickType: Int) =
            container.guiPage.components.asSequence().filter { it.isSelected(mouseX.toInt() - guiLeft, mouseY.toInt() - guiTop) }.map { it.onMouseClicked(mouseX, mouseY, clickType, this) }.firstOrNull()
                    ?: false

    override fun mouseDragged(mouseX: Double, mouseY: Double, clickType: Int, lastX: Double, lastY: Double) =
            container.guiPage.components.asSequence().filter { it.isSelected(mouseX.toInt() - guiLeft, mouseY.toInt() - guiTop) }.map { it.onMouseDragged(mouseX, mouseY, clickType, this) }.firstOrNull()
                    ?: false

    override fun mouseReleased(mouseX: Double, mouseY: Double, clickType: Int) =
            container.guiPage.components.asSequence().filter { it.isSelected(mouseX.toInt() - guiLeft, mouseY.toInt() - guiTop) }.map { it.onMouseReleased(mouseX, mouseY, clickType, this) }.firstOrNull()
                    ?: false

    public override fun renderTooltip(p_renderTooltip_1_: ItemStack, p_renderTooltip_2_: Int, p_renderTooltip_3_: Int) =
            super.renderTooltip(p_renderTooltip_1_, p_renderTooltip_2_, p_renderTooltip_3_)

    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (container.guiPage != container.te.guiLayout.current) {
            onClose()
            NetworkHandler.CHANNEL.sendToServer(OpenGUIPacket(container.te.pos, container.type))
            return
        }
        super.render(mouseX, mouseY, partialTicks)
    }

    fun drawItemStack(stack: ItemStack, x: Int, y: Int) {
        GlStateManager.translatef(0.0f, 0.0f, 32.0f)
        super.blitOffset = 200
        super.itemRenderer.zLevel = 200.0f
        val font = stack.item.getFontRenderer(stack) ?: this.font
        super.itemRenderer.renderItemAndEffectIntoGUI(stack, x, y)
        super.itemRenderer.renderItemOverlayIntoGUI(font, stack, x, y, null)
        super.blitOffset = 0
        super.itemRenderer.zLevel = 0.0f
    }

    @JvmOverloads
    fun drawFluidStack(stack: FluidStack, x: Int, y: Int, width: Int = 18, height: Int = 18) {
        val sprite = minecraft!!.textureMap.getSprite(stack.fluid.attributes.stillTexture)
        GlStateManager.enableBlend()
        val color = stack.fluid.attributes.color
        GlStateManager.color4f(color.redF, color.greenF, color.blueF, color.alphaF)
        blit(x + 1, y + 1, super.blitOffset, width - 2, height - 2, sprite)
        GlStateManager.disableBlend()
        if (stack.amount / 1000 > 0) {
            val s = (stack.amount / 1000).toString()
            val fr = minecraft?.fontRenderer
            fr?.drawStringWithShadow(s, x + width - 1f - fr.getStringWidth(s), y + height - 9f, 16777215)
        }
    }
}