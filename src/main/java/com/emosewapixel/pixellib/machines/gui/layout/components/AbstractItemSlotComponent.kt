package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.GUiUtils
import com.emosewapixel.pixellib.machines.gui.layout.IPropertyGUIComponent
import com.emosewapixel.pixellib.machines.gui.layout.ISlotComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import net.minecraft.client.gui.AbstractGui
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.items.IItemHandlerModifiable

abstract class AbstractItemSlotComponent(override val property: IValueProperty<IItemHandlerModifiable>, override val x: Int, override val y: Int) : ISlotComponent, IPropertyGUIComponent {
    override var texture = BaseTextures.ITEM_SLOT
    override val tooltips = mutableListOf<String>()
    override var width = 18
    override var height = 18
    var slotIndex = 0

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, xOffset: Int, yOffset: Int) {
        texture.render(xOffset + x, yOffset + y, width, height)
        val stack = property.value.getStackInSlot(slotIndex)
        if (!stack.isEmpty)
            GUiUtils.drawItemStack(stack, xOffset + x, yOffset + y)
    }

    @OnlyIn(Dist.CLIENT)
    override fun drawInForeground(mouseX: Int, mouseY: Int, xOffset: Int, yOffset: Int) {
        if (isSelected(mouseX - xOffset, mouseY - yOffset)) {
            AbstractGui.fill(xOffset + x + 1, yOffset + y + 1, width - 2, height - 2, 0xFFFFF)
            val stack = property.value.getStackInSlot(slotIndex)
            if (!stack.isEmpty)
                GUiUtils.renderTooltip(stack, mouseX, mouseY)
        }
    }

    @OnlyIn(Dist.CLIENT)
    abstract override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int): Boolean
}