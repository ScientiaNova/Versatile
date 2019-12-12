package com.emosewapixel.pixellib.machines.gui

import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.reopening.OpenGUIPacket
import com.mojang.blaze3d.platform.GLX
import com.mojang.blaze3d.platform.GlStateManager
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.math.MathHelper
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.client.event.GuiContainerEvent.DrawBackground
import net.minecraftforge.client.event.GuiContainerEvent.DrawForeground
import net.minecraftforge.common.MinecraftForge


open class BaseScreen(container: BaseContainer, playerInv: PlayerInventory, title: ITextComponent) : ContainerScreen<BaseContainer>(container, playerInv, title) {
    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) =
            container.guiPage.components.forEach { it.drawInBackground(mouseX.toDouble(), mouseY.toDouble(), guiLeft, guiTop) }

    open fun drawItems(mouseX: Int, mouseY: Int) =
            container.guiPage.components.forEach { it.drawItem(mouseX.toDouble(), mouseY.toDouble(), guiLeft, guiTop) }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) =
            container.guiPage.components.forEach { it.drawInForeground(mouseX.toDouble(), mouseY.toDouble(), guiLeft, guiTop) }

    override fun mouseClicked(mouseX: Double, mouseY: Double, clickType: Int): Boolean {
        val componentsMatch = container.guiPage.components.reversed().any { it.onMouseClicked(mouseX, mouseY, guiLeft, guiTop, clickType) }
        return if (componentsMatch) true else super.mouseClicked(mouseX, mouseY, clickType)
    }

    override fun mouseDragged(mouseX: Double, mouseY: Double, clickType: Int, lastX: Double, lastY: Double): Boolean {
        val componentsMatch = container.guiPage.components.reversed().any { it.onMouseDragged(mouseX, mouseY, guiLeft, guiTop, clickType) }
        return if (componentsMatch) true else super.mouseDragged(mouseX, mouseY, clickType, lastX, lastY)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, clickType: Int): Boolean {
        val componentsMatch = container.guiPage.components.reversed().any { it.onMouseReleased(mouseX, mouseY, guiLeft, guiTop, clickType) }
        return if (componentsMatch) true else super.mouseReleased(mouseX, mouseY, clickType)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        val componentsMatch = container.guiPage.components.reversed().any { it.keyPressed(keyCode, scanCode, modifiers, guiLeft, guiTop) }
        return if (componentsMatch) true else super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        val componentsMatch = container.guiPage.components.reversed().any { it.keyReleased(keyCode, scanCode, modifiers, guiLeft, guiTop) }
        return if (componentsMatch) true else super.keyReleased(keyCode, scanCode, modifiers)
    }

    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground()
        if (container.guiPage != container.te.guiLayout.current) {
            onClose()
            NetworkHandler.CHANNEL.sendToServer(OpenGUIPacket(container.te.pos))
            return
        }
        baseRender(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    private fun baseRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)

        MinecraftForge.EVENT_BUS.post(DrawBackground(this, mouseX, mouseY))
        GlStateManager.disableRescaleNormal()
        RenderHelper.disableStandardItemLighting()
        GlStateManager.disableLighting()
        GlStateManager.disableDepthTest()
        RenderHelper.enableGUIStandardItemLighting()
        GlStateManager.pushMatrix()
        GlStateManager.color4f(1f, 1f, 1f, 1f)
        GlStateManager.enableRescaleNormal()
        hoveredSlot = null
        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240f, 240f)
        GlStateManager.color4f(1f, 1f, 1f, 1f)

        drawItems(mouseX, mouseY)

        GlStateManager.translatef(guiLeft.toFloat(), guiTop.toFloat(), 0f)

        for (i1 in container.inventorySlots.indices) {
            val slot = container.inventorySlots[i1]
            if (slot.isEnabled) drawSlot(slot)
            if (isSlotSelected(slot, mouseX.toDouble(), mouseY.toDouble()) && slot.isEnabled) {
                hoveredSlot = slot
                GlStateManager.disableLighting()
                GlStateManager.disableDepthTest()
                val slotX = slot.xPos
                val slotY = slot.yPos
                GlStateManager.colorMask(true, true, true, false)
                val slotColor = getSlotColor(i1)
                fillGradient(slotX, slotY, slotX + 16, slotY + 16, slotColor, slotColor)
                GlStateManager.colorMask(true, true, true, true)
                GlStateManager.enableLighting()
                GlStateManager.enableDepthTest()
            }
        }

        GlStateManager.translatef(-guiLeft.toFloat(), -guiTop.toFloat(), 0f)

        RenderHelper.disableStandardItemLighting()
        drawGuiContainerForegroundLayer(mouseX, mouseY)
        RenderHelper.enableGUIStandardItemLighting()
        MinecraftForge.EVENT_BUS.post(DrawForeground(this, mouseX, mouseY))
        val playerInv = minecraft!!.player.inventory
        var itemStack = if (draggedStack.isEmpty) playerInv.itemStack else draggedStack
        if (!itemStack.isEmpty) {
            val yOffset = if (draggedStack.isEmpty) 8 else 16
            var altText: String? = null
            if (!draggedStack.isEmpty && isRightMouseClick) {
                itemStack = itemStack.copy()
                itemStack.count = MathHelper.ceil(itemStack.count.toFloat() / 2f)
            } else if (dragSplitting && dragSplittingSlots.size > 1) {
                itemStack = itemStack.copy()
                itemStack.count = dragSplittingRemnant
                if (itemStack.isEmpty)
                    altText = "" + TextFormatting.YELLOW + "0"
            }
            GlStateManager.translatef(0f, 0f, 32f)
            GUiUtils.drawItemStack(itemStack, mouseX - 8, mouseY - yOffset, altText = altText)
        }

        GlStateManager.popMatrix()
        GlStateManager.enableLighting()
        GlStateManager.enableDepthTest()
        RenderHelper.enableStandardItemLighting()
    }

    override fun init() {
        xSize = container.guiPage.trueWidth
        ySize = container.guiPage.trueHeight
        super.init()
    }
}