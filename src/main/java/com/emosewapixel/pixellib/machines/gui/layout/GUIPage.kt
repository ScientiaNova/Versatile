package com.emosewapixel.pixellib.machines.gui.layout

class GUIPage {
    private var foreground = GUIPagePart()
    private var background = GUIPagePart()

    fun foreground(layout: GUIPagePart.() -> Unit) = foreground.layout()
    fun background(layout: GUIPagePart.() -> Unit) = background.layout()
}