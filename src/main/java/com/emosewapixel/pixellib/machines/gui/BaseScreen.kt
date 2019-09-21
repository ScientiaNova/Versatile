package com.emosewapixel.pixellib.machines.gui

import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.ReopenGUIPacket
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.text.ITextComponent

class BaseScreen(container: BaseContainer, playerInv: PlayerInventory, title: ITextComponent) : ContainerScreen<BaseContainer>(container, playerInv, title) {
    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        container.guiPage.components.forEach { it.drawInBackground(mouseX, mouseY, this) }
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        container.guiPage.components.forEach { it.drawInForeground(mouseX, mouseY, this) }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, clickType: Int) =
            container.guiPage.components.asSequence().filter { it.isSelected(mouseX.toInt() - guiLeft, mouseY.toInt() - guiTop) }.map { it.onMouseClicked(mouseX, mouseY, clickType, this) }.firstOrNull()
                    ?: false

    override fun mouseDragged(mouseX: Double, mouseY: Double, clickType: Int, lastX: Double, lastY: Double) =
            container.guiPage.components.asSequence().filter { it.isSelected(mouseX.toInt() - guiLeft, mouseY.toInt() - guiTop) }.map { it.onMouseDragged(mouseX, mouseY, clickType, this) }.firstOrNull()
                    ?: false

    override fun mouseReleased(mouseX: Double, mouseY: Double, clickType: Int) =
            container.guiPage.components.asSequence().filter { it.isSelected(mouseX.toInt() - guiLeft, mouseY.toInt() - guiTop) }.map { it.onMouseReleased(mouseX, mouseY, clickType, this) }.firstOrNull()
                    ?: false

    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (container.guiPage != container.te.guiLayout.current) {
            onClose()
            NetworkHandler.CHANNEL.sendToServer(ReopenGUIPacket(container.te.pos, container.type))
            return
        }
        super.render(mouseX, mouseY, partialTicks)
    }
}