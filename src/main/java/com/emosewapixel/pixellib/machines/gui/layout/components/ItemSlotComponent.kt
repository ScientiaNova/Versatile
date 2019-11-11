package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import net.minecraft.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.items.IItemHandlerModifiable
import org.lwjgl.glfw.GLFW
import kotlin.math.ceil

open class ItemSlotComponent(property: IValueProperty<IItemHandlerModifiable>, x: Int, y: Int) : AbstractItemSlotComponent(property, x, y) {
    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        val itemHandler = property.value
        val playerInv = screen.container.playerInv
        val heldStack = playerInv.itemStack
        if (heldStack.isEmpty) {
            if (!itemHandler.getStackInSlot(slotIndex).isEmpty) {
                val stack = itemHandler.getStackInSlot(slotIndex)
                when (clickType) {
                    GLFW.GLFW_MOUSE_BUTTON_LEFT -> {
                        playerInv.itemStack = stack
                        itemHandler.setStackInSlot(slotIndex, ItemStack.EMPTY)
                    }
                    GLFW.GLFW_MOUSE_BUTTON_RIGHT -> {
                        val count = ceil(stack.count / 2f).toInt()
                        playerInv.itemStack = ItemStack(stack.item, count)
                        stack.shrink(count)
                    }
                    GLFW.GLFW_MOUSE_BUTTON_MIDDLE ->
                        if (playerInv.player.isCreative)
                            playerInv.itemStack = stack.copy()
                }
            }
        } else if (itemHandler.isItemValid(slotIndex, heldStack))
            when (itemHandler.getStackInSlot(slotIndex).isEmpty) {
                true -> when (clickType) {
                    GLFW.GLFW_MOUSE_BUTTON_LEFT ->
                        playerInv.itemStack = itemHandler.insertItem(slotIndex, heldStack, false)
                    GLFW.GLFW_MOUSE_BUTTON_RIGHT ->
                        if (itemHandler.insertItem(slotIndex, ItemStack(heldStack.item, 1), false).isEmpty)
                            playerInv.itemStack.shrink(1)
                }
                false -> {
                    val stack = itemHandler.getStackInSlot(slotIndex)
                    if (stack.isItemEqual(heldStack) && stack.tag == heldStack.tag)
                        playerInv.itemStack = itemHandler.insertItem(slotIndex, heldStack, false)
                    else {
                        playerInv.itemStack = stack
                        itemHandler.setStackInSlot(slotIndex, heldStack)
                    }
                }
            }
        return true
    }
}