package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.capabilities.IFluidHandlerModifiable
import com.emosewapixel.pixellib.machines.gui.layout.IPropertyGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.Direction2D
import com.emosewapixel.pixellib.machines.gui.textures.GUITexture
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import net.minecraft.client.gui.AbstractGui
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class FluidBarComponent(override val property: IValueProperty<IFluidHandlerModifiable>, val backgroundText: GUITexture, override val x: Int, override val y: Int) : IPropertyGUIComponent {
    override val tooltips = mutableListOf<String>()
    override var width = 24
    override var height = 16
    var direction = Direction2D.RIGHT
    val tankId = 0

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int) {
        backgroundText.render(xOffset + x, yOffset + y, width, height)
        val tank = property.value
        if (tank.getFluidInTank(tankId).amount == 0) return
        val currentSize = tank.getTankCapacity(tankId) / tank.getFluidInTank(tankId).amount * when (direction) {
            Direction2D.RIGHT, Direction2D.LEFT -> width
            Direction2D.UP, Direction2D.DOWN -> height
        }
        when (direction) {
            Direction2D.UP -> AbstractGui.fill(xOffset + x, yOffset + y + height - currentSize, width, currentSize, tank.getFluidInTank(tankId).fluid.attributes.color)
            Direction2D.DOWN -> AbstractGui.fill(xOffset + x, yOffset + y, width, currentSize, tank.getFluidInTank(tankId).fluid.attributes.color)
            Direction2D.LEFT -> AbstractGui.fill(xOffset + x + width - currentSize, yOffset + y, currentSize, height, tank.getFluidInTank(tankId).fluid.attributes.color)
            Direction2D.RIGHT -> AbstractGui.fill(xOffset + x, yOffset + y, currentSize, height, tank.getFluidInTank(tankId).fluid.attributes.color)
        }
    }
}