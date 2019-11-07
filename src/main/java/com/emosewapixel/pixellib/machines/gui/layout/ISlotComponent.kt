package com.emosewapixel.pixellib.machines.gui.layout

import com.emosewapixel.pixellib.machines.gui.textures.GUITexture

interface ISlotComponent : IInteractableGUIComponent {
    val width: Int
    val height: Int
    val texture: GUITexture

    override fun isSelected(mouseX: Int, mouseY: Int) = x + 1 < mouseX && mouseX < x + width - 2 && y + 1 < mouseY && mouseY < y + height - 2
}