package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IInteractableGUIComponent
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateIntPacket
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.network.PacketDistributor

open class ProgressBarComponent(val backGroundTex: TextureAtlasSprite, val fillTexture: ResourceLocation, val property: String, val max: BaseScreen.() -> Int) : IInteractableGUIComponent {
    override val tooltips = mutableListOf<String>()
    var direction = Direction2D.RIGHT
    override var x = 79
    override var y = 34
    var width = 24
    var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        AbstractGui.blit(screen.guiLeft + x, screen.guiTop + y, screen.blitOffset, width, height, backGroundTex)
        screen.minecraft.textureManager.bindTexture(fillTexture)
        val currentAmount = screen.container.te.properties[property] as? Int ?: 0
        val current = if (currentAmount > 0) (max(screen).toDouble() / currentAmount * when (direction) {
            Direction2D.RIGHT, Direction2D.LEFT -> width
            Direction2D.UP, Direction2D.DOWN -> height
        }).toInt() else 0
        when (direction) {
            Direction2D.UP -> screen.blit(screen.guiLeft + x, screen.guiTop + y + height - current, 0, 0, width, current)
            Direction2D.DOWN -> screen.blit(screen.guiLeft + x, screen.guiTop + y, 0, 0, width, current)
            Direction2D.LEFT -> screen.blit(screen.guiLeft + x + width - current, screen.guiTop + y, 0, 0, current, height)
            Direction2D.RIGHT -> screen.blit(screen.guiLeft + x, screen.guiTop + y, 0, 0, current, height)
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    override fun detectAndSendChanges(container: BaseContainer) {
        val serverProperty = container.te.properties[property] as? Int ?: 0
        if (container.clientProperties[property] != serverProperty) {
            container.clientProperties[property] = serverProperty
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with { container.playerInv.player as ServerPlayerEntity }, UpdateIntPacket(container.te.pos, property, serverProperty))
        }
    }
}