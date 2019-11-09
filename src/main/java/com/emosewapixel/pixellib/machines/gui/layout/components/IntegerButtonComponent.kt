package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IInteractableGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.GUITexture
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateIntPacket
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.network.PacketDistributor

open class IntegerButtonComponent(val property: String, val textures: List<GUITexture>, override val x: Int, override val y: Int) : IInteractableGUIComponent {
    override val tooltips = mutableListOf<String>()
    var width = 16
    var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        val value = screen.container.te.properties[property] as? Int ?: 0
        textures.getOrNull(value)?.render(screen.guiLeft + x, screen.guiTop + y, width, height)
    }

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        val value = (screen.container.te.properties[property] as? Int) ?: 0
        val newValue = if (value + 1 < textures.size) value + 1 else 0
        screen.container.clientProperties[property] = newValue
        NetworkHandler.CHANNEL.sendToServer(UpdateIntPacket(screen.container.te.pos, property, newValue))
        return true
    }

    override fun detectAndSendChanges(container: BaseContainer) {
        val serverProperty = container.te.properties[property] as? Int ?: 0
        if (container.clientProperties[property] != serverProperty) {
            container.clientProperties[property] = serverProperty
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with { container.playerInv.player as ServerPlayerEntity }, UpdateIntPacket(container.te.pos, property, serverProperty))
        }
    }
}