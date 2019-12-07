package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.layout.DefaultSizeConstants
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.gui.textures.IRenderable
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class ImageComponent : IGUIComponent {
    var texture: IRenderable = BaseTextures.BACKGROUND
    override var x = 0
    override var y = 0
    override var width = DefaultSizeConstants.BACKGROUND_WIDTH
    override var height = DefaultSizeConstants.BACKGROUND_HEIGHT

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) =
            texture.render(xOffset + x, yOffset + y, width, height)

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Double, mouseY: Double) = false
}