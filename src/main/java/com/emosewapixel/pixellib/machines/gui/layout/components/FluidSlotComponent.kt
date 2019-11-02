package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.capabilities.IFluidHandlerModifiable
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.ISlotComponent
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateFluidStackPacket
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.fml.network.PacketDistributor

class FluidSlotComponent(val property: String, override val x: Int, override val y: Int, override val texture: TextureAtlasSprite) : ISlotComponent {
    override val tooltips = mutableListOf<String>()
    override var width = 18
    override var height = 18
    var tankId = 0

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        AbstractGui.blit(screen.guiLeft + x, screen.guiTop + y, screen.blitOffset, width, height, texture)
        val fluid = (screen.container.te.properties[property] as? IFluidHandler)?.getFluidInTank(tankId)
                ?: FluidStack.EMPTY
        if (!fluid.isEmpty)
            screen.drawFluidStack(fluid, screen.guiLeft + x, screen.guiTop + y, width, height)
    }

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        return true
    }

    @OnlyIn(Dist.CLIENT)
    override fun drawInForeground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        if (isSelected(mouseX - screen.guiLeft, mouseY - screen.guiTop)) {
            AbstractGui.fill(screen.guiLeft + x + 1, screen.guiTop + y + 1, width - 2, height - 2, 0xFFFFF)
            val handler = screen.container.te.properties[property] as? IFluidHandler
            val fluid = handler?.getFluidInTank(tankId)
                    ?: FluidStack.EMPTY
            if (fluid.isEmpty)
                screen.renderTooltip(TranslationTextComponent("gui.tooltip.tank_fill").string, mouseX, mouseY)
            else
                screen.renderTooltip(mutableListOf(fluid.fluid.attributes.getDisplayName(fluid).string, "${fluid.amount}mb/${handler!!.getTankCapacity(tankId)}mb", TranslationTextComponent("gui.tooltip.tank_empty").string), mouseX, mouseY)
        }
    }

    override fun detectAndSendChanges(container: BaseContainer) {
        val serverProperty = (container.te.properties[property] as? IFluidHandler)?.getFluidInTank(tankId)
                ?: FluidStack.EMPTY
        if (serverProperty != (container.clientProperties[property] as? IFluidHandler)?.getFluidInTank(tankId)) {
            (container.clientProperties[property] as? IFluidHandlerModifiable)?.setFluidInTank(tankId, serverProperty)
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with { container.playerInv.player as ServerPlayerEntity }, UpdateFluidStackPacket(container.te.pos, property, tankId, serverProperty))
        }
    }
}