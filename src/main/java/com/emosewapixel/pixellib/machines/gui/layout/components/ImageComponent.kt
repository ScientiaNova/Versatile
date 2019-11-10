package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.gui.textures.GUITexture
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class ImageComponent : IGUIComponent {
    var texture: GUITexture = BaseTextures.BACKGROUND
    override val tooltips = mutableListOf<String>()
    override var x = 0
    override var y = 0
    override var width = 176
    override var height = 166

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) = texture.render(screen.guiLeft + x, screen.guiTop + y, width, height)

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Int, mouseY: Int) = false
}