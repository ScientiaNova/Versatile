package com.scientianova.versatile.machines.gui.slots

import com.scientianova.versatile.common.extensions.isNotEmpty
import com.scientianova.versatile.common.extensions.toStack
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import org.lwjgl.glfw.GLFW

open class GhostItemSlot(handler: IItemHandler, index: Int, x: Int, y: Int) : ItemHandlerSlot(handler, index, x, y) {
    override fun onClick() = SlotLogic.Custom { slot, mouseButton, _, player ->
        val heldStack = player.inventory.itemStack
        if (heldStack.isEmpty)
            when (mouseButton) {
                GLFW.GLFW_MOUSE_BUTTON_LEFT -> slot.stack.shrink(1)
                GLFW.GLFW_MOUSE_BUTTON_RIGHT -> slot.stack.grow(1)
                GLFW.GLFW_MOUSE_BUTTON_MIDDLE -> slot.putStack(ItemStack.EMPTY)
            }
        else when (mouseButton) {
            GLFW.GLFW_MOUSE_BUTTON_LEFT -> slot.putStack(heldStack.copy())
            GLFW.GLFW_MOUSE_BUTTON_RIGHT -> slot.putStack(heldStack.item.toStack())
            GLFW.GLFW_MOUSE_BUTTON_MIDDLE -> slot.putStack(ItemStack.EMPTY)
        }
        val slotStack = slot.stack
        if (slotStack.isNotEmpty) slotStack.copy() else ItemStack.EMPTY
    }
}