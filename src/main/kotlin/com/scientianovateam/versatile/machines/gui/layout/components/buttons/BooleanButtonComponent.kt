package com.scientianovateam.versatile.machines.gui.layout.components.buttons

import com.scientianovateam.versatile.machines.gui.layout.components.ITexturedGUIComponent
import com.scientianovateam.versatile.machines.gui.textures.interactable.ButtonDrawMode
import com.scientianovateam.versatile.machines.gui.textures.interactable.ButtonTextureGroup
import com.scientianovateam.versatile.machines.properties.IVariableProperty
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class BooleanButtonComponent(val property: IVariableProperty<Boolean>, override val texture: ButtonTextureGroup, override var x: Int, override var y: Int) : ITexturedGUIComponent {
    override var width = 16
    override var height = 16

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) = texture.draw(xOffset + x, yOffset + y, width, height, drawMode = when {
        property.value -> ButtonDrawMode.ON
        isSelected(mouseX - xOffset, mouseY - yOffset) -> ButtonDrawMode.SELECTED
        else -> ButtonDrawMode.OFF
    })

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Double, mouseY: Double) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    @OnlyIn(Dist.CLIENT)
    override fun onMouseClicked(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, clickType: Int): Boolean {
        if (!isSelected(mouseX - xOffset, mouseY - yOffset)) return false
        property.setValue(!property.value)
        return true
    }

    override fun addProperties() = setOf(property)
}