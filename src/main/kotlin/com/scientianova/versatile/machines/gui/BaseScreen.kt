package com.scientianova.versatile.machines.gui

import com.scientianovateam.versatile.machines.packets.NetworkHandler
import com.scientianovateam.versatile.machines.packets.reopening.OpenGUIPacket
import com.mojang.blaze3d.platform.GLX
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
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

    override fun charTyped(char: Char, modifiers: Int): Boolean {
        val componentsMatch = container.guiPage.components.reversed().any { it.charTyped(char, modifiers, guiLeft, guiTop) }
        return if (componentsMatch) true else super.charTyped(char, modifiers)
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
        if (container.guiPage != container.te.guiLayout.currentPage) {
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
        RenderSystem.disableRescaleNormal()
        RenderHelper.disableStandardItemLighting()
        RenderSystem.disableLighting()
        RenderSystem.disableDepthTest()
        RenderSystem.pushMatrix()
        RenderSystem.color4f(1f, 1f, 1f, 1f)
        RenderSystem.enableRescaleNormal()
        hoveredSlot = null
        RenderSystem.glMultiTexCoord2f(33986, 240.0f, 240.0f)
        RenderSystem.color4f(1f, 1f, 1f, 1f)

        drawItems(mouseX, mouseY)

        RenderSystem.translatef(guiLeft.toFloat(), guiTop.toFloat(), 0f)

        for (slot in container.inventorySlots) {
            if (slot.isEnabled) drawSlot(slot)
            if (isSlotSelected(slot, mouseX.toDouble(), mouseY.toDouble()) && slot.isEnabled) {
                hoveredSlot = slot
                RenderSystem.disableLighting()
                RenderSystem.disableDepthTest()
                val slotX = slot.xPos
                val slotY = slot.yPos
                RenderSystem.colorMask(true, true, true, false)
                val slotColor = getSlotColor(slot.slotIndex)
                fillGradient(slotX, slotY, slotX + 16, slotY + 16, slotColor, slotColor)
                RenderSystem.colorMask(true, true, true, true)
                RenderSystem.enableLighting()
                RenderSystem.enableDepthTest()
            }
        }

        RenderSystem.translatef(-guiLeft.toFloat(), -guiTop.toFloat(), 0f)

        RenderHelper.disableStandardItemLighting()
        drawGuiContainerForegroundLayer(mouseX, mouseY)
        MinecraftForge.EVENT_BUS.post(DrawForeground(this, mouseX, mouseY))
        val playerInv = minecraft!!.player!!.inventory
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
            RenderSystem.translatef(0f, 0f, 32f)
            GUiUtils.drawItemStack(itemStack, mouseX - 8, mouseY - yOffset, altText = altText)
        }

        RenderSystem.popMatrix()
        RenderSystem.enableLighting()
        RenderSystem.enableDepthTest()
    }

    override fun init() {
        xSize = container.guiPage.trueWidth
        ySize = container.guiPage.trueHeight
        super.init()
    }
}