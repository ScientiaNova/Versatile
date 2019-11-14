package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.layout.IPropertyGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.GUITexturePair
import com.emosewapixel.pixellib.machines.properties.IVariableProperty
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class BooleanButtonComponent(override val property: IVariableProperty<Boolean>, val texture: GUITexturePair, override val x: Int, override val y: Int) : IPropertyGUIComponent {
    override val tooltips = mutableListOf<String>()
    override var width = 16
    override var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int) = texture.render(xOffset + x, yOffset + y, width, height, drawFirst = property.value)

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Double, mouseY: Double) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int): Boolean {
        property.setValue(!property.value)
        return true
    }
}