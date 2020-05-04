package com.scientianova.versatile.machines.gui.layout.components.bars

import com.scientianova.versatile.machines.gui.layout.components.ITexturedGUIComponent
import com.scientianova.versatile.machines.gui.textures.BaseTextures
import com.scientianova.versatile.machines.gui.textures.updating.Direction2D
import com.scientianova.versatile.machines.properties.IValueProperty
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class ProgressBarComponent(val property: IValueProperty<Double>) : ITexturedGUIComponent {
    override var texture = BaseTextures.ARROW_BAR
    var direction = Direction2D.RIGHT
    override var x = 77
    override var y = 34
    override var width = 22
    override var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) =
            texture.draw(xOffset + x, yOffset + y, width, height, property.value, direction)

    override fun addProperties() = setOf(property)
}