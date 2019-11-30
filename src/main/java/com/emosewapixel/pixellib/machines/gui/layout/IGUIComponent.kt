package com.emosewapixel.pixellib.machines.gui.layout

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

interface IGUIComponent {
    var x: Int
    var y: Int
    val width: Int
    val height: Int

    @OnlyIn(Dist.CLIENT)
    fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int): Boolean = false

    @OnlyIn(Dist.CLIENT)
    fun onMouseDragged(mouseX: Double, mouseY: Double, clickType: Int): Boolean = false

    @OnlyIn(Dist.CLIENT)
    fun onMouseReleased(mouseX: Double, mouseY: Double, clickType: Int): Boolean = false

    @OnlyIn(Dist.CLIENT)
    fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int)

    @OnlyIn(Dist.CLIENT)
    fun isSelected(mouseX: Double, mouseY: Double) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    @OnlyIn(Dist.CLIENT)
    fun drawInForeground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int) {
    }

    fun offset(xOffset: Int, yOffset: Int) {
        x += xOffset
        y += yOffset
    }
}