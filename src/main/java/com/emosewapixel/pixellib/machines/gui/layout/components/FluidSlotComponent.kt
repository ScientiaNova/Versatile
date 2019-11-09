package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.ktlib.extensions.isNotEmpty
import com.emosewapixel.ktlib.extensions.times
import com.emosewapixel.pixellib.items.ItemStackHolder
import com.emosewapixel.pixellib.machines.capabilities.IFluidHandlerModifiable
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.ISlotComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateFluidStackPacket
import net.minecraft.client.gui.AbstractGui
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.fml.network.PacketDistributor

open class FluidSlotComponent(val property: String, override val x: Int, override val y: Int) : ISlotComponent {
    override var texture = BaseTextures.FLUID_SLOT
    override val tooltips = mutableListOf<String>()
    override var width = 18
    override var height = 18
    var tankId = 0

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        texture.render(screen.guiLeft + x, screen.guiTop + y, width, height)
        val fluid = (screen.container.te.properties[property] as? IFluidHandler)?.getFluidInTank(tankId)
                ?: FluidStack.EMPTY
        if (!fluid.isEmpty)
            screen.drawFluidStack(fluid, screen.guiLeft + x, screen.guiTop + y, width, height)
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

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        val slotTank = screen.container.te.properties[property] as? IFluidHandlerModifiable
        val playerInv = screen.container.playerInv
        val heldStack = playerInv.itemStack
        heldStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent { handler ->
            val itemTanks = (0 until handler.tanks).map { index -> index to handler.getFluidInTank(index) }
            val stackInSelected = slotTank?.getFluidInTank(tankId)
            val stackCount = heldStack.count
            when (stackInSelected?.isEmpty) {
                //Filling Tank, Emptying Container
                true -> {
                    val capacity = slotTank.getTankCapacity(tankId)
                    itemTanks.firstOrNull { it.second.isNotEmpty && slotTank.isFluidValid(tankId, it.second) }?.second?.fluid?.let { fluid ->
                        val totalAmount = itemTanks.filter { it.second.fluid == fluid }.map { it.second.amount }.sum()
                        val totalInStack = totalAmount * stackCount
                        val fullyConsumed: Int
                        val leftOver: Int
                        if (capacity >= totalInStack) {
                            fullyConsumed = stackCount
                            leftOver = 0
                        } else {
                            fullyConsumed = totalInStack / totalAmount
                            leftOver = totalInStack % totalAmount
                        }
                        val resultStack = ItemStackHolder()
                        var extraStack = ItemStack.EMPTY
                        val fullyConsumedAmount = fullyConsumed * stackCount
                        val fullyConsumedStack = fluid * fullyConsumedAmount
                        if (fullyConsumed > 0 && handler.drain(fullyConsumedStack, IFluidHandler.FluidAction.SIMULATE).amount == fullyConsumedAmount) {
                            slotTank.setFluidInTank(tankId, fullyConsumedStack)
                            val copy = heldStack.copy()
                            copy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent {
                                it.drain(fullyConsumedStack, IFluidHandler.FluidAction.EXECUTE)
                            }
                            resultStack.changeOrAdd(copy)
                            heldStack.shrink(fullyConsumed)
                        }
                        val leftOverStack = fluid * leftOver
                        if (leftOver > 0 && handler.drain(leftOverStack, IFluidHandler.FluidAction.SIMULATE).amount == leftOver) {
                            if (slotTank.getFluidInTank(tankId).isEmpty)
                                slotTank.setFluidInTank(tankId, leftOverStack)
                            else slotTank.getFluidInTank(tankId).amount += leftOver
                            val copy = heldStack.copy()
                            copy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent {
                                it.drain(leftOverStack, IFluidHandler.FluidAction.EXECUTE)
                            }
                            extraStack = resultStack.changeOrAdd(copy)
                            heldStack.shrink(1)
                        }
                        if (heldStack.isEmpty) {
                            playerInv.itemStack = resultStack.stack
                            if (extraStack.isNotEmpty)
                                playerInv.addItemStackToInventory(extraStack)
                        } else {
                            if (resultStack.isNotEmpty)
                                playerInv.addItemStackToInventory(resultStack.stack)
                            if (extraStack.isNotEmpty)
                                playerInv.addItemStackToInventory(extraStack)
                        }
                    }
                }
                false -> when {
                    //Emptying Tank, Filling Container
                    itemTanks.all { it.second.isEmpty } -> {
                        val fillable = handler.fill(stackInSelected, IFluidHandler.FluidAction.SIMULATE)
                        if (fillable > 0) {
                            val fullyFilled = stackInSelected.amount / fillable
                            val leftOver = stackInSelected.amount % fillable
                            val resultStack = ItemStackHolder()
                            var extraStack = ItemStack.EMPTY
                            val fullyFillable = fullyFilled * fillable
                            if (fullyFilled > 0) {
                                stackInSelected.amount -= fullyFillable
                                val copy = heldStack.copy()
                                copy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent {
                                    it.fill(stackInSelected.fluid * fullyFillable, IFluidHandler.FluidAction.EXECUTE)
                                }
                                resultStack.changeOrAdd(copy)
                                heldStack.shrink(fullyFilled)
                            }
                            if (leftOver > 0) {
                                stackInSelected.amount -= leftOver
                                val copy = heldStack.copy()
                                copy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent {
                                    it.fill(stackInSelected.fluid * leftOver, IFluidHandler.FluidAction.EXECUTE)
                                }
                                extraStack = resultStack.changeOrAdd(copy)
                                heldStack.shrink(1)
                            }
                            if (heldStack.isEmpty) {
                                playerInv.itemStack = resultStack.stack
                                if (extraStack.isNotEmpty)
                                    playerInv.addItemStackToInventory(extraStack)
                            } else {
                                if (resultStack.isNotEmpty)
                                    playerInv.addItemStackToInventory(resultStack.stack)
                                if (extraStack.isNotEmpty)
                                    playerInv.addItemStackToInventory(extraStack)
                            }
                        }
                    }
                    //Filling Tank, Emptying Container
                    itemTanks.any { it.second.fluid == stackInSelected.fluid } -> {
                        val capacity = slotTank.getTankCapacity(tankId) - stackInSelected.amount
                        val fluid = stackInSelected.fluid
                        val totalAmount = itemTanks.filter { it.second.fluid == fluid }.map { it.second.amount }.sum()
                        val totalInStack = totalAmount * stackCount
                        val fullyConsumed: Int
                        val leftOver: Int
                        if (capacity >= totalInStack) {
                            fullyConsumed = stackCount
                            leftOver = 0
                        } else {
                            fullyConsumed = totalInStack / totalAmount
                            leftOver = totalInStack % totalAmount
                        }
                        val resultStack = ItemStackHolder()
                        var extraStack = ItemStack.EMPTY
                        val fullyConsumedAmount = fullyConsumed * stackCount
                        val fullyConsumedStack = fluid * fullyConsumedAmount
                        if (fullyConsumed > 0 && handler.drain(fullyConsumedStack, IFluidHandler.FluidAction.SIMULATE).amount == fullyConsumedAmount) {
                            slotTank.getFluidInTank(tankId).amount += fullyConsumedAmount
                            val copy = heldStack.copy()
                            copy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent {
                                it.drain(fullyConsumedStack, IFluidHandler.FluidAction.EXECUTE)
                            }
                            resultStack.changeOrAdd(copy)
                            heldStack.shrink(fullyConsumed)
                        }
                        val leftOverStack = fluid * leftOver
                        if (leftOver > 0 && handler.drain(leftOverStack, IFluidHandler.FluidAction.SIMULATE).amount == leftOver) {
                            slotTank.getFluidInTank(tankId).amount += leftOver
                            val copy = heldStack.copy()
                            copy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent {
                                it.drain(leftOverStack, IFluidHandler.FluidAction.EXECUTE)
                            }
                            extraStack = resultStack.changeOrAdd(copy)
                            heldStack.shrink(1)
                        }
                        if (heldStack.isEmpty) {
                            playerInv.itemStack = resultStack.stack
                            if (extraStack.isNotEmpty)
                                playerInv.addItemStackToInventory(extraStack)
                        } else {
                            if (resultStack.isNotEmpty)
                                playerInv.addItemStackToInventory(resultStack.stack)
                            if (extraStack.isNotEmpty)
                                playerInv.addItemStackToInventory(extraStack)
                        }
                    }
                    //Replacing?
                    else -> {

                    }
                }
            }
        }
        return true
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