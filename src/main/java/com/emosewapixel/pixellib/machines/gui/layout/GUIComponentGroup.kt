package com.emosewapixel.pixellib.machines.gui.layout

import com.emosewapixel.pixellib.machines.gui.slots.IImprovedSlot
import com.emosewapixel.pixellib.machines.properties.IMachineProperty
import net.minecraft.entity.player.PlayerInventory
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class GUIComponentGroup @JvmOverloads constructor(extraWidth: Int = 0, extraHeight: Int = 0) : GUIPage(extraWidth, extraHeight), IGUIComponent {
    override var x = 0
    override var y = 0

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) = components.forEach {
        it.drawInBackground(mouseX, mouseY, xOffset + x, yOffset + y, guiLeft, guiTop)
    }

    @OnlyIn(Dist.CLIENT)
    override fun drawItem(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) = components.forEach {
        it.drawItem(mouseX, mouseY, xOffset + x, yOffset + y, guiLeft, guiTop)
    }

    @OnlyIn(Dist.CLIENT)
    override fun drawInForeground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) = components.forEach {
        it.drawInForeground(mouseX, mouseY, xOffset + x, yOffset + y, guiLeft, guiTop)
    }

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Double, mouseY: Double) = components.any { it.isSelected(mouseX - x, mouseY - y) }

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int) = components.any {
        it.onMouseClicked(mouseX, mouseY, xOffset + x, yOffset + y, clickType)
    }

    @OnlyIn(Dist.CLIENT)
    override fun onMouseDragged(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int) = components.any {
        it.onMouseDragged(mouseX, mouseY, xOffset + x, yOffset + y, clickType)
    }

    @OnlyIn(Dist.CLIENT)
    override fun onMouseReleased(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int) = components.any {
        it.onMouseReleased(mouseX, mouseY, xOffset + x, yOffset + y, clickType)
    }

    @OnlyIn(Dist.CLIENT)
    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int, xOffset: Int, yOffset: Int) = components.any {
        it.keyPressed(keyCode, scanCode, modifiers, xOffset + x, yOffset + y)
    }

    @OnlyIn(Dist.CLIENT)
    override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int, xOffset: Int, yOffset: Int) = components.any {
        it.keyReleased(keyCode, scanCode, modifiers, xOffset + x, yOffset + y)
    }

    override fun addSlots(playerInv: PlayerInventory, xOffset: Int, yOffset: Int) = components.fold(mutableListOf<IImprovedSlot>()) { acc, current ->
        acc += current.addSlots(playerInv, xOffset + x, yOffset + y)
        acc
    }

    override fun addProperties() = components.fold(mutableSetOf<IMachineProperty>()) { acc, curr ->
        acc += curr.addProperties()
        acc
    }

    override fun unwrap() = components.flatMap { it.unwrap().map { base -> OffsetGUIComponent(base.component, base.xOffset + x, base.yOffset + y) } }
}