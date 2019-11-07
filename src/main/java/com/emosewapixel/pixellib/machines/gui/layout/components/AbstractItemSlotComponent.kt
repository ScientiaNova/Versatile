package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.ISlotComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateItemStackPacket
import net.minecraft.client.gui.AbstractGui
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.network.PacketDistributor
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.IItemHandlerModifiable

abstract class AbstractItemSlotComponent(val property: String, override val x: Int, override val y: Int) : ISlotComponent {
    override var texture = BaseTextures.ITEM_SLOT
    override val tooltips = mutableListOf<String>()
    override var width = 18
    override var height = 18
    var slotIndex = 0

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        texture.render(screen.guiLeft + x, screen.guiTop + y, width, height)
        val stack = (screen.container.te.properties[property] as? IItemHandler)?.getStackInSlot(slotIndex)
                ?: ItemStack.EMPTY
        if (!stack.isEmpty)
            screen.drawItemStack(stack, screen.guiLeft + x, screen.guiTop + y)
    }

    @OnlyIn(Dist.CLIENT)
    override fun drawInForeground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        if (isSelected(mouseX - screen.guiLeft, mouseY - screen.guiTop)) {
            AbstractGui.fill(screen.guiLeft + x + 1, screen.guiTop + y + 1, width - 2, height - 2, 0xFFFFF)
            val handler = screen.container.te.properties[property] as? IItemHandler
            val stack = handler?.getStackInSlot(slotIndex)
                    ?: ItemStack.EMPTY
            if (!stack.isEmpty)
                screen.renderTooltip(stack, mouseX, mouseY)
        }
    }

    @OnlyIn(Dist.CLIENT)
    abstract override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean

    override fun detectAndSendChanges(container: BaseContainer) {
        val serverProperty = (container.te.properties[property] as? IItemHandler)?.getStackInSlot(slotIndex)
                ?: ItemStack.EMPTY
        if (serverProperty != (container.clientProperties[property] as? IItemHandler)?.getStackInSlot(slotIndex)) {
            (container.clientProperties[property] as? IItemHandlerModifiable)?.setStackInSlot(slotIndex, serverProperty)
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with { container.playerInv.player as ServerPlayerEntity }, UpdateItemStackPacket(container.te.pos, property, slotIndex, serverProperty))
        }
    }
}