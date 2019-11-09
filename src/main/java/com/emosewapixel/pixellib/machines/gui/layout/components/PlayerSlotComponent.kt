package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.ktlib.extensions.insertItem
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.ISlotComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
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
    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        texture.render(screen.guiLeft + x, screen.guiTop + y, width, height)
        val stack = screen.container.playerInv.getStackInSlot(slotIndex)
        if (!stack.isEmpty)
            screen.drawItemStack(stack, screen.guiLeft + x, screen.guiTop + y)
    }

    @OnlyIn(Dist.CLIENT)
    override fun drawInForeground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        if (isSelected(mouseX - screen.guiLeft, mouseY - screen.guiTop)) {
            AbstractGui.fill(screen.guiLeft + x + 1, screen.guiTop + y + 1, width - 2, height - 2, 0xFFFFF)
            val stack = screen.container.playerInv.getStackInSlot(slotIndex)
            if (!stack.isEmpty)
                screen.renderTooltip(stack, mouseX, mouseY)
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        val playerInv = screen.container.playerInv
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

    override fun detectAndSendChanges(container: BaseContainer) {}
}