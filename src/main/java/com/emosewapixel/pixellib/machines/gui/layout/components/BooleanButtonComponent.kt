package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IInteractableGUIComponent
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateBooleanPacket
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.network.PacketDistributor

class BooleanButtonComponent(val property: String, val onTex: TextureAtlasSprite, val offTex: TextureAtlasSprite, override val x: Int, override val y: Int) : IInteractableGUIComponent {
    override val tooltips = mutableListOf<String>()
    var width = 16
    var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        val bool = screen.container.te.properties[property] as? Boolean ?: false
        AbstractGui.blit(screen.guiLeft + x, screen.guiTop + y, screen.blitOffset, width, height, if (bool) onTex else offTex)

    }

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        val newValue = (screen.container.te.properties[property] as? Boolean)?.not() ?: false
        screen.container.clientProperties[property] = newValue
        NetworkHandler.CHANNEL.sendToServer(UpdateBooleanPacket(screen.container.te.pos, property, newValue))
        return true
    }

    override fun detectAndSendChanges(container: BaseContainer) {
        val serverProperty = container.te.properties[property] as? Boolean ?: false
        if (container.clientProperties[property] != serverProperty) {
            container.clientProperties[property] = serverProperty
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with { container.playerInv.player as ServerPlayerEntity }, UpdateBooleanPacket(container.te.pos, property, serverProperty))
        }
    }
}