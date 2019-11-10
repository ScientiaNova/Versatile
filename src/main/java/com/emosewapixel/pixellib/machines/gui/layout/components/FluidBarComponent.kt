package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IPropertyGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.Direction2D
import com.emosewapixel.pixellib.machines.gui.textures.GUITexture
import com.emosewapixel.pixellib.machines.properties.IFluidHandlerProperty
import net.minecraft.client.gui.AbstractGui
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class FluidBarComponent(override val property: IFluidHandlerProperty, val backGroundText: GUITexture, override val x: Int, override val y: Int) : IPropertyGUIComponent {
    override val tooltips = mutableListOf<String>()
    override var width = 24
    override var height = 16
    var direction = Direction2D.RIGHT
    val tankId = 0

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        backGroundText.render(screen.guiLeft + x, screen.guiTop + y, width, height)
        val tank = property.handler
        if (tank.getFluidInTank(tankId).amount == 0) return
        val currentSize = tank.getTankCapacity(tankId) / tank.getFluidInTank(tankId).amount * when (direction) {
            Direction2D.RIGHT, Direction2D.LEFT -> width
            Direction2D.UP, Direction2D.DOWN -> height
        }
        when (direction) {
            Direction2D.UP -> AbstractGui.fill(screen.guiLeft + x, screen.guiTop + y + height - currentSize, width, currentSize, tank.getFluidInTank(tankId).fluid.attributes.color)
            Direction2D.DOWN -> AbstractGui.fill(screen.guiLeft + x, screen.guiTop + y, width, currentSize, tank.getFluidInTank(tankId).fluid.attributes.color)
            Direction2D.LEFT -> AbstractGui.fill(screen.guiLeft + x + width - currentSize, screen.guiTop + y, currentSize, height, tank.getFluidInTank(tankId).fluid.attributes.color)
            Direction2D.RIGHT -> AbstractGui.fill(screen.guiLeft + x, screen.guiTop + y, currentSize, height, tank.getFluidInTank(tankId).fluid.attributes.color)
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height
}