package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.ISlotComponent
import net.minecraft.client.gui.AbstractGui
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

class FluidSlotCompoment(val property: String, override val x: Int, override val y: Int, override val texture: ResourceLocation) : ISlotComponent {
    override val tooltips = mutableListOf<String>()
    override var width = 18
    override var height = 18
    var tankId = 0

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        screen.minecraft.textureManager.bindTexture(texture)
        screen.blit(screen.guiLeft + x, screen.guiTop + y, 0, 0, width, height)
        val fluid = (screen.container.te.properties[property] as? IFluidHandler)?.getFluidInTank(tankId)
                ?: FluidStack.EMPTY
        if (!fluid.isEmpty)
            screen.drawFluidStack(fluid, screen.guiLeft + x, screen.guiTop + y, width, height)
    }

    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        return true
    }

    override fun drawInForeground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        if (isSelected(mouseX - screen.guiLeft, mouseY - screen.guiTop)) {
            AbstractGui.fill(screen.guiLeft + x + 1, screen.guiTop + y + 1, width - 2, height - 2, 0xFFFFF)
            val handler = screen.container.te.properties[property] as? IFluidHandler
            val fluid = handler?.getFluidInTank(tankId)
                    ?: FluidStack.EMPTY
            if (fluid.isEmpty)
                screen.renderTooltip(TranslationTextComponent("gui.tooltip.tank_fill").string, mouseX, mouseY)
            else
                screen.renderTooltip(mutableListOf(fluid.fluid.attributes.getDisplayName(fluid).string, "${fluid.amount}mb/${handler!!.getTankCapacity(tankId)}mb", TranslationTextComponent("gui.tooltip.tank_empty").string), mouseX, mouseY)
        }
    }
}