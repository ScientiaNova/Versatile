package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.IDrawable

interface ITexturedGUIComponent : IGUIComponent {
    val texture: IDrawable

    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) =
            texture.draw(xOffset + x, yOffset + y, width, height)
}