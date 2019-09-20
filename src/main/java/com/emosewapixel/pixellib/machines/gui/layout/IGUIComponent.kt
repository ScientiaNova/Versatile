package com.emosewapixel.pixellib.machines.gui.layout

import com.emosewapixel.pixellib.machines.gui.BaseScreen

interface IGUIComponent {
    val x: Int
    val y: Int
    val tooltip: String?

    fun onMouseClicked(properties: MutableMap<String, Any>, mouseX: Float, mouseY: Float, clickType: Int)
    fun render(screen: BaseScreen)
}