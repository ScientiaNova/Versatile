package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.items.IItemHandlerModifiable
import org.lwjgl.glfw.GLFW

open class GhostItemSlotComponent(property: IValueProperty<IItemHandlerModifiable>, x: Int, y: Int) : AbstractItemSlotComponent(property, x, y) {
    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        val itemHandler = property.value
        val heldStack = screen.container.playerInv.itemStack
        if (heldStack.isEmpty) {
            if (!itemHandler.getStackInSlot(slotIndex).isEmpty) {
                val stack = itemHandler.getStackInSlot(slotIndex)
                when (clickType) {
                    GLFW.GLFW_MOUSE_BUTTON_LEFT -> stack.shrink(1)
                    GLFW.GLFW_MOUSE_BUTTON_RIGHT -> stack.grow(1)
                }
            }
        } else itemHandler.setStackInSlot(slotIndex, heldStack.copy())
        return true
    }
}