package com.emosewapixel.pixellib.machines.gui

import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.OpenGUIPacket
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.text.ITextComponent

open class BaseScreen(container: BaseContainer, playerInv: PlayerInventory, title: ITextComponent) : ContainerScreen<BaseContainer>(container, playerInv, title) {
    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) =
            container.guiPage.components.forEach { it.drawInBackground(mouseX.toDouble(), mouseY.toDouble(), guiLeft, guiTop) }

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
        super.render(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    override fun init() {
        xSize = container.guiPage.trueWidth
        ySize = container.guiPage.trueHeight
        super.init()
    }
}