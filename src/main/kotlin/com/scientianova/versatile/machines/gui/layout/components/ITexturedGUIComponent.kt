package com.scientianova.versatile.machines.gui.layout.components

import com.scientianovateam.versatile.machines.gui.layout.IGUIComponent
import com.scientianovateam.versatile.machines.gui.textures.IDrawable

interface ITexturedGUIComponent : IGUIComponent {
    val texture: IDrawable

    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) =
            texture.draw(xOffset + x, yOffset + y, width, height)
}