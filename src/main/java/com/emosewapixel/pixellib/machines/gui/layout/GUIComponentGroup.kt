package com.emosewapixel.pixellib.machines.gui.layout

open class GUIComponentGroup : GUIPage(), IGUIComponent {
    override var x = 0
    override var y = 0

    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int) = components.forEach {
        it.drawInBackground(mouseX, mouseY, xOffset + x, yOffset + y)
    }

    override fun drawInForeground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int) = components.forEach {
        it.drawInForeground(mouseX, mouseY, xOffset + x, yOffset + y)
    }

    override fun isSelected(mouseX: Double, mouseY: Double) = components.any { it.isSelected(mouseX - x, mouseY - y) }

    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int) = components.any {
        it.onMouseClicked(mouseX - x, mouseY - y, clickType)
    }

    override fun onMouseDragged(mouseX: Double, mouseY: Double, clickType: Int) = components.any {
        it.onMouseDragged(mouseX - x, mouseY - y, clickType)
    }

    override fun onMouseReleased(mouseX: Double, mouseY: Double, clickType: Int) = components.any {
        it.onMouseReleased(mouseX - x, mouseY - y, clickType)
    }
}