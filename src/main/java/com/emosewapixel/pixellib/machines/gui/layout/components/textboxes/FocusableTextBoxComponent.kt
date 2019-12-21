package com.emosewapixel.pixellib.machines.gui.layout.components.textboxes

import com.emosewapixel.pixellib.machines.properties.implementations.strings.VariableStringProperty
import org.lwjgl.glfw.GLFW

open class FocusableTextBoxComponent(property: VariableStringProperty) : TextBoxComponent(property) {
    override var isEnabled = false

    override fun onMouseClicked(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int): Boolean {
        isEnabled = isSelected(mouseX, mouseY)
        return false
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int, xOffset: Int, yOffset: Int): Boolean {
        if (isEnabled && keyCode == GLFW.GLFW_KEY_ENTER) {
            isEnabled = false
            return true
        }
        return super.keyPressed(keyCode, scanCode, modifiers, xOffset, yOffset)
    }
}