package com.scientianovateam.versatile.machines.gui.layout.components.still

import com.scientianovateam.versatile.machines.gui.layout.DefaultSizeConstants
import com.scientianovateam.versatile.machines.gui.layout.components.ITexturedGUIComponent
import com.scientianovateam.versatile.machines.gui.textures.BaseTextures
import com.scientianovateam.versatile.machines.gui.textures.IDrawable
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class ImageComponent : ITexturedGUIComponent {
    override var x = 0
    override var y = 0
    override var width = DefaultSizeConstants.BACKGROUND_WIDTH
    override var height = DefaultSizeConstants.BACKGROUND_HEIGHT
    override var texture: IDrawable = BaseTextures.BACKGROUND

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Double, mouseY: Double) = false
}