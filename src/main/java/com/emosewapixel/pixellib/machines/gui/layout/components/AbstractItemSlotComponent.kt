package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.ISlotComponent
import net.minecraft.client.gui.AbstractGui
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.items.IItemHandler

abstract class AbstractItemSlotComponent(val property: String, override val x: Int, override val y: Int, override val texture: ResourceLocation) : ISlotComponent {
    override val tooltips = mutableListOf<String>()
    override var width = 18
    override var height = 18
    var slotIndex = 0

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        screen.minecraft.textureManager.bindTexture(texture)
        val stack = (screen.container.te.properties[property] as? IItemHandler)?.getStackInSlot(slotIndex)
                ?: ItemStack.EMPTY
        if (!stack.isEmpty)
            screen.drawItemStack(stack, screen.guiLeft + x, screen.guiTop + y)
    }

    override fun drawInForeground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        if (isSelected(mouseX - screen.guiLeft, mouseY - screen.guiTop)) {
            AbstractGui.fill(screen.guiLeft + x + 1, screen.guiTop + y + 1, width - 2, height - 2, 0xFFFFF)
            val handler = screen.container.te.properties[property] as? IItemHandler
            val stack = handler?.getStackInSlot(slotIndex)
                    ?: ItemStack.EMPTY
            if (!stack.isEmpty)
                screen.renderTooltip(stack, mouseX, mouseY)
        }
    }

    abstract override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean
}