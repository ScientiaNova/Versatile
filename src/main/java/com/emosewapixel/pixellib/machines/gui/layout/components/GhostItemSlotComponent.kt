package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.items.IItemHandlerModifiable
import org.lwjgl.glfw.GLFW

class GhostItemSlotComponent(property: String, x: Int, y: Int, texture: TextureAtlasSprite) : AbstractItemSlotComponent(property, x, y, texture) {
    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        val itemHandler = screen.container.te.properties[property] as? IItemHandlerModifiable
        val playerInv = screen.container.playerInv
        val heldStack = playerInv.itemStack
        if (heldStack.isEmpty) {
            if (itemHandler?.getStackInSlot(slotIndex)?.isEmpty == false) {
                val stack = itemHandler.getStackInSlot(slotIndex)
                when (clickType) {
                    GLFW.GLFW_MOUSE_BUTTON_LEFT -> {
                        stack.shrink(1)
                    }
                    GLFW.GLFW_MOUSE_BUTTON_RIGHT -> {
                        stack.grow(1)
                    }
                }
            }
        } else itemHandler?.setStackInSlot(slotIndex, heldStack.copy())
        return true
    }
}