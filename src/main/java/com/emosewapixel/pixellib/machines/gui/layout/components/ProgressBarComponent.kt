package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IPropertyGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.gui.textures.Direction2D
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class ProgressBarComponent(override val property: IValueProperty<Double>) : IPropertyGUIComponent {
    var bar = BaseTextures.ARROW_BAR
    override val tooltips = mutableListOf<String>()
    var direction = Direction2D.RIGHT
    override var x = 79
    override var y = 34
    override var width = 24
    override var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) = bar.render(screen.guiLeft + x, screen.guiTop + y, width, height, property.value, direction)

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height
}