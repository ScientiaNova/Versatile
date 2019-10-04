package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.packets.ChangePagePacket
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.ReopenGUIPacket
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.fml.network.PacketDistributor

class PageButtonComponent(val texture: TextureAtlasSprite, val page: Int, override val x: Int, override val y: Int) : IGUIComponent {
    override val tooltips = mutableListOf<String>()
    var width = 16
    var height = 16

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) =
            AbstractGui.blit(screen.guiLeft + x, screen.guiTop + y, screen.blitOffset, width, height, texture)

    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        screen.container.te.guiLayout.current = screen.container.te.guiLayout[page]
        NetworkHandler.CHANNEL.sendToServer(ChangePagePacket(screen.container.te.pos, page))
        return true
    }

    override fun detectAndSendChanges(container: BaseContainer) {
        if (container.guiPage != container.te.guiLayout.current)
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with { container.playerInv.player as ServerPlayerEntity }, ReopenGUIPacket(container.te.pos, container.type))
    }
}