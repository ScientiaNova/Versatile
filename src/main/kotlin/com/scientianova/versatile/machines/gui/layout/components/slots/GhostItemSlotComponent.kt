package com.scientianova.versatile.machines.gui.layout.components.slots

import com.scientianovateam.versatile.machines.properties.IValueProperty
import net.minecraft.client.Minecraft
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.items.IItemHandlerModifiable
import org.lwjgl.glfw.GLFW

open class GhostItemSlotComponent(property: IValueProperty<out IItemHandlerModifiable>, slotIndex: Int) : ItemSlotComponent(property, slotIndex) {
    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int): Boolean {
        val itemHandler = property.value
        val heldStack = Minecraft.getInstance().player!!.inventory.itemStack
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