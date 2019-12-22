package com.scientianovateam.versatile.machines.gui.textures

import com.scientianovateam.versatile.machines.gui.textures.interactable.ButtonTextureGroup
import com.scientianovateam.versatile.machines.gui.textures.still.GUITexture
import com.scientianovateam.versatile.machines.gui.textures.updating.ProgressBar

object BaseTextures {
    @JvmField
    val BACKGROUND = GUITexture("versatile:textures/gui/background.png")

    @JvmField
    val ITEM_SLOT = GUITexture("versatile:textures/gui/item_slot.png")

    @JvmField
    val FLUID_SLOT = GUITexture("versatile:textures/gui/fluid_slot.png")

    @JvmField
    val ARROW_BAR = ProgressBar(GUITexture("versatile:textures/gui/basic_arrow_background.png"), GUITexture("versatile:textures/gui/basic_arrow_foreground.png"))

    @JvmField
    val TEXT_BOX = ButtonTextureGroup(GUITexture("versatile:textures/gui/active_test_box.png"), GUITexture("versatile:textures/gui/inactive_test_box.png"))
}