package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.extensions.isNotEmpty
import com.emosewapixel.pixellib.extensions.times
import com.emosewapixel.pixellib.items.ItemStackHolder
import com.emosewapixel.pixellib.machines.capabilities.IFluidHandlerModifiable
import com.emosewapixel.pixellib.machines.gui.GUiUtils
import com.emosewapixel.pixellib.machines.gui.layout.IPropertyGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler

open class FluidSlotComponent(override val property: IValueProperty<IFluidHandlerModifiable>, override val x: Int, override val y: Int) : IPropertyGUIComponent {
    var texture = BaseTextures.FLUID_SLOT
    override val tooltips = mutableListOf<String>()
    override var width = 18
    override var height = 18
    var tankId = 0

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int) {
        texture.render(xOffset + x, yOffset + y, width, height)
        val fluid = property.value.getFluidInTank(tankId)
        if (!fluid.isEmpty)
            GUiUtils.drawFluidStack(fluid, xOffset + x, yOffset + y, width, height)
    }

    @OnlyIn(Dist.CLIENT)
    override fun drawInForeground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int) {
        if (isSelected(mouseX - xOffset, mouseY - yOffset)) {
            AbstractGui.fill(xOffset + x + 1, yOffset + y + 1, width - 2, height - 2, 0xFFFFF)
            val handler = property.value
            val fluid = handler.getFluidInTank(tankId)
            if (fluid.isEmpty)
                GUiUtils.renderTooltip(listOf(TranslationTextComponent("gui.tooltip.tank_fill").string), mouseX, mouseY)
            else
                GUiUtils.renderTooltip(mutableListOf(fluid.fluid.attributes.getDisplayName(fluid).string, "${fluid.amount}mb/${handler.getTankCapacity(tankId)}mb", TranslationTextComponent("gui.tooltip.tank_empty").string), mouseX, mouseY)
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int): Boolean {
        val slotTank = property.value
        val playerInv = Minecraft.getInstance().player.inventory
        val heldStack = playerInv.itemStack
        heldStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent { handler ->
            val itemTanks = (0 until handler.tanks).map { index -> index to handler.getFluidInTank(index) }
            val stackInSelected = slotTank.getFluidInTank(tankId)
            val stackCount = heldStack.count
            when (stackInSelected.isEmpty) {
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
}