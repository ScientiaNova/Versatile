package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.capabilities.IMutableFluidTank
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IInteractableGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.Direction2D
import com.emosewapixel.pixellib.machines.gui.textures.GUITexture
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateFluidTankPacket
import net.minecraft.client.gui.AbstractGui
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.fml.network.PacketDistributor

class FluidBarComponent(val property: String, val backGroundText: GUITexture, override val x: Int, override val y: Int) : IInteractableGUIComponent {
    override val tooltips = mutableListOf<String>()
    var direction = Direction2D.RIGHT
    val tankId = 0
    var width = 24
    var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        backGroundText.render(screen.guiLeft + x, screen.guiTop + y, width, height)
        val tank = (screen.container.te.properties[property] as? IFluidHandler)
        val currentSize = (tank?.getTankCapacity(tankId) ?: 0) / (tank?.getFluidInTank(tankId)?.amount
                ?: 1) * when (direction) {
            Direction2D.RIGHT, Direction2D.LEFT -> width
            Direction2D.UP, Direction2D.DOWN -> height
        }
        when (direction) {
            Direction2D.UP -> AbstractGui.fill(screen.guiLeft + x, screen.guiTop + y + height - currentSize, width, currentSize, tank?.getFluidInTank(tankId)?.fluid?.attributes?.color
                    ?: -1)
            Direction2D.DOWN -> AbstractGui.fill(screen.guiLeft + x, screen.guiTop + y, width, currentSize, tank?.getFluidInTank(tankId)?.fluid?.attributes?.color
                    ?: -1)
            Direction2D.LEFT -> AbstractGui.fill(screen.guiLeft + x + width - currentSize, screen.guiTop + y, currentSize, height, tank?.getFluidInTank(tankId)?.fluid?.attributes?.color
                    ?: -1)
            Direction2D.RIGHT -> AbstractGui.fill(screen.guiLeft + x, screen.guiTop + y, currentSize, height, tank?.getFluidInTank(tankId)?.fluid?.attributes?.color
                    ?: -1)
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    override fun detectAndSendChanges(container: BaseContainer) {
        val serverProperty = (container.te.properties[property] as? IMutableFluidTank)?.fluid
        if (serverProperty != (container.clientProperties[property] as? IMutableFluidTank)?.fluid) {
            (container.clientProperties[property] as? IMutableFluidTank)?.fluid = serverProperty!!
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with { container.playerInv.player as ServerPlayerEntity }, UpdateFluidTankPacket(container.te.pos, property, serverProperty))
        }
    }
}