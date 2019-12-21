package com.emosewapixel.pixellib.machines.gui.layout

import com.emosewapixel.pixellib.machines.gui.slots.IImprovedSlot
import com.emosewapixel.pixellib.machines.properties.IMachineProperty
import net.minecraft.entity.player.PlayerInventory
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

interface IGUIComponent {
    var x: Int
    var y: Int
    val width: Int
    val height: Int

    @OnlyIn(Dist.CLIENT)
    fun onMouseClicked(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int) = false

    @OnlyIn(Dist.CLIENT)
    fun onMouseDragged(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int) = false

    @OnlyIn(Dist.CLIENT)
    fun onMouseReleased(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int) = false

    @OnlyIn(Dist.CLIENT)
    fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int, xOffset: Int, yOffset: Int) = false

    @OnlyIn(Dist.CLIENT)
    fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int, xOffset: Int, yOffset: Int) = false

    @OnlyIn(Dist.CLIENT)
    fun charTyped(char: Char, modifier: Int, xOffset: Int, yOffset: Int) = false

    @OnlyIn(Dist.CLIENT)
    fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int = xOffset, guiTop: Int = yOffset)

    @OnlyIn(Dist.CLIENT)
    fun drawItem(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int = xOffset, guiTop: Int = yOffset) {}

    @OnlyIn(Dist.CLIENT)
    fun drawInForeground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int = xOffset, guiTop: Int = yOffset) {
    }

    @OnlyIn(Dist.CLIENT)
    fun isSelected(mouseX: Double, mouseY: Double) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    fun addSlots(playerInv: PlayerInventory, xOffset: Int = 0, yOffset: Int = 0) = emptyList<IImprovedSlot>()

    fun addProperties() = emptySet<IMachineProperty>()

    fun offset(xOffset: Int, yOffset: Int) {
        x += xOffset
        y += yOffset
    }

    fun unwrap() = listOf(OffsetGUIComponent(this))
}