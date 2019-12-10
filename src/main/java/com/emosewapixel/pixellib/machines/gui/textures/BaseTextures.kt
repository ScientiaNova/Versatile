package com.emosewapixel.pixellib.machines.gui.textures

import com.emosewapixel.pixellib.machines.gui.textures.still.GUITexture
import com.emosewapixel.pixellib.machines.gui.textures.updating.ProgressBar

object BaseTextures {
    @JvmField
    val BACKGROUND = GUITexture("pixellib:textures/gui/background.png")

    @JvmField
    val ITEM_SLOT = GUITexture("pixellib:textures/gui/item_slot.png")

    @JvmField
    val FLUID_SLOT = GUITexture("pixellib:textures/gui/fluid_slot.png")

    @JvmField
    val ARROW_BAR = ProgressBar(GUITexture("pixellib:textures/gui/basic_arrow_background.png"), GUITexture("pixellib:textures/gui/basic_arrow_foreground.png"))
}