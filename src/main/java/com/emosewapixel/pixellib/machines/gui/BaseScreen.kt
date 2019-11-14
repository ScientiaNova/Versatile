package com.emosewapixel.pixellib.machines.gui

import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.OpenGUIPacket
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.text.ITextComponent

class BaseScreen(container: BaseContainer, playerInv: PlayerInventory, title: ITextComponent) : ContainerScreen<BaseContainer>(container, playerInv, title) {
    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) =
            container.guiPage.components.forEach { it.drawInBackground(mouseX.toDouble(), mouseY.toDouble(), guiLeft, guiTop) }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) =
            container.guiPage.components.forEach { it.drawInForeground(mouseX.toDouble(), mouseY.toDouble(), guiLeft, guiTop) }

    override fun mouseClicked(mouseX: Double, mouseY: Double, clickType: Int) =
            container.guiPage.components.reversed().asSequence().filter { it.isSelected(mouseX - guiLeft, mouseY - guiTop) }.any { it.onMouseClicked(mouseX, mouseY, clickType) }

    override fun mouseDragged(mouseX: Double, mouseY: Double, clickType: Int, lastX: Double, lastY: Double) =
            container.guiPage.components.reversed().asSequence().filter { it.isSelected(mouseX - guiLeft, mouseY - guiTop) }.any { it.onMouseDragged(mouseX, mouseY, clickType) }

    override fun mouseReleased(mouseX: Double, mouseY: Double, clickType: Int) =
            container.guiPage.components.reversed().asSequence().filter { it.isSelected(mouseX - guiLeft, mouseY - guiTop) }.any { it.onMouseReleased(mouseX, mouseY, clickType) }

    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (container.guiPage != container.te.guiLayout.current) {
            onClose()
            NetworkHandler.CHANNEL.sendToServer(OpenGUIPacket(container.te.pos, container.type))
            return
        }
        super.render(mouseX, mouseY, partialTicks)
    }
}