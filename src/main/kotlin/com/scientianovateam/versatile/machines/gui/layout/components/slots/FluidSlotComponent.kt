package com.scientianovateam.versatile.machines.gui.layout.components.slots

import com.scientianovateam.versatile.common.extensions.isNotEmpty
import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.items.ItemStackHolder
import com.scientianovateam.versatile.machines.capabilities.fluids.FluidStackHandler
import com.scientianovateam.versatile.machines.capabilities.fluids.IFluidHandlerModifiable
import com.scientianovateam.versatile.machines.gui.GUiUtils
import com.scientianovateam.versatile.machines.gui.layout.DefaultSizeConstants
import com.scientianovateam.versatile.machines.gui.layout.components.ITexturedGUIComponent
import com.scientianovateam.versatile.machines.gui.textures.BaseTextures
import com.scientianovateam.versatile.machines.packets.NetworkHandler
import com.scientianovateam.versatile.machines.packets.UpdateHeldStackPacket
import com.scientianovateam.versatile.machines.packets.handlers.UpdateTankPacket
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.properties.IValueProperty
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
import kotlin.math.min

open class FluidSlotComponent(val property: IValueProperty<out IFluidHandlerModifiable>, val tankIndex: Int = 0) : ITexturedGUIComponent {
    override var texture = BaseTextures.FLUID_SLOT
    override var width = DefaultSizeConstants.SLOT_WIDTH
    override var height = DefaultSizeConstants.SLOT_HEIGHT
    override var x = 0
    override var y = 0

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        texture.draw(xOffset + x, yOffset + y, width, height)
        val fluid = property.value.getFluidInTank(tankIndex)
        if (!fluid.isEmpty)
            GUiUtils.drawFluidStack(fluid, xOffset + x + 1, yOffset + y + 1, width - 2, height - 2)
    }

    @OnlyIn(Dist.CLIENT)
    override fun drawInForeground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        if (isSelected(mouseX - xOffset, mouseY - yOffset)) {
            GUiUtils.drawColoredRectangle(0x7FFFFFFF, xOffset + x + 1, yOffset + y + 1, width - 2, height - 2)
            val handler = property.value
            val fluid = handler.getFluidInTank(tankIndex)

            val tooltips = if (fluid.isEmpty)
                listOf(TranslationTextComponent("gui.tooltip.empty").formattedText, TranslationTextComponent("gui.tooltip.tank_fill").formattedText)
            else
                listOf(fluid.fluid.attributes.getDisplayName(fluid).string, "${fluid.amount}/${handler.getTankCapacity(tankIndex)} mB", TranslationTextComponent("gui.tooltip.tank_empty").formattedText)

            GUiUtils.drawTooltip(tooltips, mouseX, mouseY)
        }
    }

    override fun isSelected(mouseX: Double, mouseY: Double) = x + 1 < mouseX && mouseX < x + width - 2 && y + 1 < mouseY && mouseY < y + height - 2

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int): Boolean {
        if (!isSelected(mouseX - xOffset, mouseY - yOffset)) return false
        val slotTank = property.value
        val playerInv = Minecraft.getInstance().player!!.inventory
        val heldStack = playerInv.itemStack
        heldStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent { handler ->
            val itemTanks = (0 until handler.tanks).map { index -> index to handler.getFluidInTank(index) }
            val selectedTank = slotTank.getFluidInTank(tankIndex)
            val stackCount = heldStack.count
            if (selectedTank.isEmpty) {
                //Filling Tank, Emptying Container
                val capacity = slotTank.getTankCapacity(tankIndex)
                itemTanks.firstOrNull { it.second.isNotEmpty && slotTank.isFluidValid(tankIndex, it.second) }?.second?.fluid?.let { fluid ->
                    val fluidPerItem = itemTanks.filter { it.second.fluid == fluid }.map { it.second.amount }.sum()
                    val fluidInStack = fluidPerItem * stackCount
                    val fullyConsumableItems: Int
                    val leftOverFluid: Int
                    if (capacity >= fluidInStack) {
                        fullyConsumableItems = stackCount
                        leftOverFluid = 0
                    } else {
                        fullyConsumableItems = fluidInStack / fluidPerItem
                        leftOverFluid = fluidInStack % fluidPerItem
                    }
                    val resultStack = ItemStackHolder()
                    var extraStack = ItemStack.EMPTY
                    val fullyConsumableAmount = fullyConsumableItems * fluidPerItem
                    val fullyConsumableFluid = fluid * fullyConsumableAmount
                    if (fullyConsumableItems > 0 && handler.drain(fullyConsumableFluid, IFluidHandler.FluidAction.SIMULATE).amount == fullyConsumableAmount) {
                        slotTank.setFluidInTank(tankIndex, fullyConsumableFluid)
                        heldStack.copy().getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent {
                            it.drain(fullyConsumableFluid, IFluidHandler.FluidAction.EXECUTE)
                            resultStack.changeOrAdd(it.container)
                        }
                        heldStack.shrink(fullyConsumableItems)
                    }
                    val leftOverStack = fluid * leftOverFluid
                    if (leftOverFluid > 0 && handler.drain(leftOverStack, IFluidHandler.FluidAction.SIMULATE).amount == leftOverFluid) {
                        if (slotTank.getFluidInTank(tankIndex).isEmpty)
                            slotTank.setFluidInTank(tankIndex, leftOverStack)
                        else {
                            slotTank.getFluidInTank(tankIndex).amount += leftOverFluid
                            if (slotTank is FluidStackHandler) slotTank.onContentsChanged(listOf(tankIndex))
                        }
                        heldStack.copy().getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent {
                            it.drain(leftOverStack, IFluidHandler.FluidAction.EXECUTE)
                            extraStack = resultStack.changeOrAdd(it.container)
                        }
                        heldStack.shrink(1)
                    }
                    if (heldStack.isEmpty) {
                        playerInv.itemStack = resultStack.stack
                        NetworkHandler.CHANNEL.sendToServer(UpdateHeldStackPacket(resultStack.stack))
                        if (extraStack.isNotEmpty)
                            playerInv.addItemStackToInventory(extraStack)
                    } else {
                        if (resultStack.isNotEmpty)
                            playerInv.addItemStackToInventory(resultStack.stack)
                        if (extraStack.isNotEmpty)
                            playerInv.addItemStackToInventory(extraStack)
                    }
                    if (resultStack.isNotEmpty || extraStack.isNotEmpty)
                        updateOnServer()
                }
            } else when {
                //Emptying Tank, Filling Container
                itemTanks.all { it.second.isEmpty } -> {
                    val fillablePerItem = handler.fill(selectedTank, IFluidHandler.FluidAction.SIMULATE)
                    if (fillablePerItem > 0) {
                        val fillableItems = min(selectedTank.amount / fillablePerItem, heldStack.count)
                        val leftOver = if (fillableItems < heldStack.count) selectedTank.amount % fillablePerItem else 0
                        val resultStack = ItemStackHolder()
                        var extraStack = ItemStack.EMPTY
                        val fillableInStack = fillableItems * fillablePerItem
                        if (fillableItems > 0) {
                            heldStack.copy().getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent {
                                it.fill(selectedTank.fluid * fillableInStack, IFluidHandler.FluidAction.EXECUTE)
                                resultStack.changeOrAdd(it.container)
                            }
                            selectedTank.amount -= fillableInStack
                            if (slotTank is FluidStackHandler) slotTank.onContentsChanged(listOf(tankIndex))
                            heldStack.shrink(fillableItems)
                        }
                        if (leftOver > 0) {
                            heldStack.copy().getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent {
                                it.fill(selectedTank.fluid * leftOver, IFluidHandler.FluidAction.EXECUTE)
                                extraStack = resultStack.changeOrAdd(it.container)
                            }
                            selectedTank.amount -= leftOver
                            if (slotTank is FluidStackHandler) slotTank.onContentsChanged(listOf(tankIndex))
                            heldStack.shrink(1)
                        }
                        if (heldStack.isEmpty) {
                            playerInv.itemStack = resultStack.stack
                            NetworkHandler.CHANNEL.sendToServer(UpdateHeldStackPacket(resultStack.stack))
                            if (extraStack.isNotEmpty)
                                playerInv.addItemStackToInventory(extraStack)
                        } else {
                            if (resultStack.isNotEmpty)
                                playerInv.addItemStackToInventory(resultStack.stack)
                            if (extraStack.isNotEmpty)
                                playerInv.addItemStackToInventory(extraStack)
                        }
                        if (resultStack.isNotEmpty || extraStack.isNotEmpty)
                            updateOnServer()
                    }
                }
                //Filling Tank, Emptying Container
                itemTanks.any { it.second.fluid == selectedTank.fluid } -> {
                    val capacity = slotTank.getTankCapacity(tankIndex) - selectedTank.amount
                    val fluid = selectedTank.fluid
                    val fluidPerItem = itemTanks.filter { it.second.fluid == fluid }.map { it.second.amount }.sum()
                    val fluidInStack = fluidPerItem * stackCount
                    val fullyConsumableItems: Int
                    val leftOver: Int
                    if (capacity >= fluidInStack) {
                        fullyConsumableItems = stackCount
                        leftOver = 0
                    } else {
                        fullyConsumableItems = fluidInStack / fluidPerItem
                        leftOver = fluidInStack % fluidPerItem
                    }
                    val resultStack = ItemStackHolder()
                    var extraStack = ItemStack.EMPTY
                    val fullyConsumableAmount = fullyConsumableItems * fluidPerItem
                    val fullyConsumableFluid = fluid * fullyConsumableAmount
                    if (fullyConsumableItems > 0 && handler.drain(fullyConsumableFluid, IFluidHandler.FluidAction.SIMULATE).amount == fullyConsumableAmount) {
                        slotTank.getFluidInTank(tankIndex).amount += fullyConsumableAmount
                        if (slotTank is FluidStackHandler) slotTank.onContentsChanged(listOf(tankIndex))
                        heldStack.copy().getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent {
                            it.drain(fullyConsumableFluid, IFluidHandler.FluidAction.EXECUTE)
                            resultStack.changeOrAdd(it.container)
                        }
                        heldStack.shrink(fullyConsumableItems)
                    }
                    val leftOverStack = fluid * leftOver
                    if (leftOver > 0 && handler.drain(leftOverStack, IFluidHandler.FluidAction.SIMULATE).amount == leftOver) {
                        slotTank.getFluidInTank(tankIndex).amount += leftOver
                        if (slotTank is FluidStackHandler) slotTank.onContentsChanged(listOf(tankIndex))
                        heldStack.copy().getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent {
                            it.drain(leftOverStack, IFluidHandler.FluidAction.EXECUTE)
                            extraStack = resultStack.changeOrAdd(it.container)
                        }
                        heldStack.shrink(1)
                    }
                    if (heldStack.isEmpty) {
                        playerInv.itemStack = resultStack.stack
                        NetworkHandler.CHANNEL.sendToServer(UpdateHeldStackPacket(resultStack.stack))
                        if (extraStack.isNotEmpty)
                            playerInv.addItemStackToInventory(extraStack)
                    } else {
                        if (resultStack.isNotEmpty)
                            playerInv.addItemStackToInventory(resultStack.stack)
                        if (extraStack.isNotEmpty)
                            playerInv.addItemStackToInventory(extraStack)
                    }
                    if (resultStack.isNotEmpty || extraStack.isNotEmpty)
                        updateOnServer()
                }
                //Replacing?
                else -> {

                }
            }
        }
        return true
    }

    private fun updateOnServer() {
        val slotTank = property.value
        if (property is ITEBoundProperty)
            NetworkHandler.CHANNEL.sendToServer(UpdateTankPacket(property.id, tankIndex, slotTank.getFluidInTank(tankIndex)))
    }

    override fun addProperties() = setOf(property)
}