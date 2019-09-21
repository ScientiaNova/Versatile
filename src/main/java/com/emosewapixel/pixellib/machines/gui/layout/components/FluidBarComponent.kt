package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import net.minecraft.client.gui.AbstractGui
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.IFluidTank

class FluidBarComponent(val backgroundText: ResourceLocation, val tank: () -> IFluidTank) : IGUIComponent {
    override val tooltips = mutableListOf<String>()
    var direction = Direction2D.RIGHT
    override var x = 79
    override var y = 34
    var width = 24
    var height = 16

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        screen.minecraft.textureManager.bindTexture(backgroundText)
        screen.blit(screen.guiLeft + x, screen.guiTop + y, 0, 0, width, height)
        val current = currentSize
        when (direction) {
            Direction2D.UP -> AbstractGui.fill(screen.guiLeft + x, screen.guiTop + y + height - current, width, current, tank().fluid.fluid.attributes.color)
            Direction2D.DOWN -> AbstractGui.fill(screen.guiLeft + x, screen.guiTop + y, width, current, tank().fluid.fluid.attributes.color)
            Direction2D.LEFT -> AbstractGui.fill(screen.guiLeft + x + width - current, screen.guiTop + y, current, height, tank().fluid.fluid.attributes.color)
            Direction2D.RIGHT -> AbstractGui.fill(screen.guiLeft + x, screen.guiTop + y, current, height, tank().fluid.fluid.attributes.color)
        }
    }

    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    val currentSize
        get() = (tank().capacity / tank().fluidAmount * when (direction) {
            Direction2D.RIGHT, Direction2D.LEFT -> width
            Direction2D.UP, Direction2D.DOWN -> height
        })
}