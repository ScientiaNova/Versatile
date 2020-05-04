package com.scientianova.versatile.machines.gui.layout.components.bars

import com.scientianova.versatile.machines.capabilities.fluids.IFluidHandlerModifiable
import com.scientianova.versatile.machines.gui.GUiUtils
import com.scientianova.versatile.machines.gui.layout.components.ITexturedGUIComponent
import com.scientianova.versatile.machines.gui.textures.updating.Direction2D
import com.scientianova.versatile.machines.gui.textures.still.GUITexture
import com.scientianova.versatile.machines.properties.IValueProperty
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class FluidBarComponent(val property: IValueProperty<out IFluidHandlerModifiable>, override val texture: GUITexture, override var x: Int, override var y: Int) : ITexturedGUIComponent {
    override var width = 24
    override var height = 16
    var direction = Direction2D.RIGHT
    val tankId = 0

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        texture.draw(xOffset + x, yOffset + y, width, height)
        val tank = property.value
        if (tank.getFluidInTank(tankId).amount == 0) return
        val currentSize = tank.getTankCapacity(tankId) / tank.getFluidInTank(tankId).amount * when (direction) {
            Direction2D.RIGHT, Direction2D.LEFT -> width
            Direction2D.UP, Direction2D.DOWN -> height
        }
        when (direction) {
            Direction2D.UP -> GUiUtils.drawColoredRectangle(x, y + height - currentSize, width, currentSize, tank.getFluidInTank(tankId).fluid.attributes.color)
            Direction2D.DOWN -> GUiUtils.drawColoredRectangle(x, y, width, currentSize, tank.getFluidInTank(tankId).fluid.attributes.color)
            Direction2D.LEFT -> GUiUtils.drawColoredRectangle(x + width - currentSize, y, currentSize, height, tank.getFluidInTank(tankId).fluid.attributes.color)
            Direction2D.RIGHT -> GUiUtils.drawColoredRectangle(x, y, currentSize, height, tank.getFluidInTank(tankId).fluid.attributes.color)
        }
    }

    override fun addProperties() = setOf(property)
}