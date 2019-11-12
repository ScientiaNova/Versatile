package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.extensions.insertItem
import com.emosewapixel.pixellib.machines.gui.GUiUtils
import com.emosewapixel.pixellib.machines.gui.layout.ISlotComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui
import net.minecraft.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import org.lwjgl.glfw.GLFW
import kotlin.math.ceil

open class PlayerSlotComponent(val slotIndex: Int, override val x: Int, override val y: Int) : ISlotComponent {
    override val texture = BaseTextures.ITEM_SLOT
    override val tooltips = mutableListOf<String>()
    override var width = 18
    override var height = 18

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, xOffset: Int, yOffset: Int) {
        texture.render(xOffset + x, yOffset + y, width, height)
        val stack = Minecraft.getInstance().player.inventory.getStackInSlot(slotIndex)
        if (!stack.isEmpty)
            GUiUtils.drawItemStack(stack, xOffset + x, yOffset + y)
    }

    @OnlyIn(Dist.CLIENT)
    override fun drawInForeground(mouseX: Int, mouseY: Int, xOffset: Int, yOffset: Int) {
        if (isSelected(mouseX - xOffset, mouseY - yOffset)) {
            AbstractGui.fill(xOffset + x + 1, yOffset + y + 1, width - 2, height - 2, 0xFFFFF)
            val stack = Minecraft.getInstance().player.inventory.getStackInSlot(slotIndex)
            if (!stack.isEmpty)
                GUiUtils.renderTooltip(stack, mouseX, mouseY)
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int): Boolean {
        val playerInv = Minecraft.getInstance().player.inventory
        val heldStack = playerInv.itemStack
        if (heldStack.isEmpty) {
            if (!playerInv.getStackInSlot(slotIndex).isEmpty) {
                val stack = playerInv.getStackInSlot(slotIndex)
                when (clickType) {
                    GLFW.GLFW_MOUSE_BUTTON_LEFT -> {
                        playerInv.itemStack = stack
                        playerInv.setInventorySlotContents(slotIndex, ItemStack.EMPTY)
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
        } else when (playerInv.getStackInSlot(slotIndex).isEmpty) {
            true -> when (clickType) {
                GLFW.GLFW_MOUSE_BUTTON_LEFT ->
                    playerInv.itemStack = playerInv.insertItem(slotIndex, heldStack, false)
                GLFW.GLFW_MOUSE_BUTTON_RIGHT ->
                    if (playerInv.insertItem(slotIndex, ItemStack(heldStack.item, 1), false).isEmpty)
                        playerInv.itemStack.shrink(1)
            }
            false -> {
                val stack = playerInv.getStackInSlot(slotIndex)
                if (stack.isItemEqual(heldStack) && stack.tag == heldStack.tag)
                    playerInv.itemStack = playerInv.insertItem(slotIndex, heldStack, false)
                else {
                    playerInv.itemStack = stack
                    playerInv.setInventorySlotContents(slotIndex, heldStack)
                }
            }
        }
        return true
    }
}