package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraftforge.fluids.capability.IFluidHandler

class FluidBarComponent(val backGroundText: TextureAtlasSprite, val property: String) : IGUIComponent {
    override val tooltips = mutableListOf<String>()
    var direction = Direction2D.RIGHT
    override var x = 79
    override var y = 34
    val tankId = 0
    var width = 24
    var height = 16

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        AbstractGui.blit(screen.guiLeft + x, screen.guiTop + y, screen.blitOffset, width, height, backGroundText)
        val tank = (screen.container.te.properties[property] as? IFluidHandler)
        val currentSize = (tank?.getTankCapacity(tankId) ?: 0) / (tank?.getFluidInTank(tankId)?.amount
                ?: 1) * when (direction) {
            Direction2D.RIGHT, Direction2D.LEFT -> width
            Direction2D.UP, Direction2D.DOWN -> height
        }
        when (direction) {
            Direction2D.UP -> AbstractGui.fill(screen.guiLeft + x, screen.guiTop + y + height - currentSize, width, currentSize, tank?.getFluidInTank(tankId)?.fluid?.attributes?.color
                    ?: -1)
            Direction2D.DOWN -> AbstractGui.fill(screen.guiLeft + x, screen.guiTop + y, width, currentSize, tank?.getFluidInTank(tankId)?.fluid?.attributes?.color
                    ?: -1)
            Direction2D.LEFT -> AbstractGui.fill(screen.guiLeft + x + width - currentSize, screen.guiTop + y, currentSize, height, tank?.getFluidInTank(tankId)?.fluid?.attributes?.color
                    ?: -1)
            Direction2D.RIGHT -> AbstractGui.fill(screen.guiLeft + x, screen.guiTop + y, currentSize, height, tank?.getFluidInTank(tankId)?.fluid?.attributes?.color
                    ?: -1)
        }
    }

    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height
}