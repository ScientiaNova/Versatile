package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IInteractableGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.gui.textures.Direction2D
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateIntPacket
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.network.PacketDistributor

open class ProgressBarComponent(val property: String, val maxProperty: String) : IInteractableGUIComponent {
    var backGroundTex = BaseTextures.ARROW_BACKGROUND
    var fillTexture = BaseTextures.ARROW_FOREGROUND
    override val tooltips = mutableListOf<String>()
    var direction = Direction2D.RIGHT
    override var x = 79
    override var y = 34
    var width = 24
    var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        backGroundTex.render(screen.guiLeft + x, screen.guiTop + y, width, height)
        val currentAmount = screen.container.te.properties[property] as? Int ?: 0
        if (currentAmount <= 0) return
        val current = ((screen.container.te.properties[maxProperty] as Double) / currentAmount * when (direction) {
            Direction2D.RIGHT, Direction2D.LEFT -> width
            Direction2D.UP, Direction2D.DOWN -> height
        }).toInt()
        when (direction) {
            Direction2D.UP -> fillTexture.render(screen.guiLeft + x, screen.guiTop + y + height - current, width, current, vStart = current / height.toDouble())
            Direction2D.DOWN -> fillTexture.render(screen.guiLeft + x, screen.guiTop + y, width, current, vEnd = current / height.toDouble())
            Direction2D.LEFT -> fillTexture.render(screen.guiLeft + x + width - current, screen.guiTop + y, current, height, uStart = current / width.toDouble())
            Direction2D.RIGHT -> fillTexture.render(screen.guiLeft + x, screen.guiTop + y, current, height, uEnd = current / width.toDouble())
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
        val serverMaxProperty = container.te.properties[maxProperty] as? Int ?: 0
        if (container.clientProperties[maxProperty] != serverMaxProperty) {
            container.clientProperties[maxProperty] = serverMaxProperty
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with { container.playerInv.player as ServerPlayerEntity }, UpdateIntPacket(container.te.pos, maxProperty, serverMaxProperty))
        }
    }
}