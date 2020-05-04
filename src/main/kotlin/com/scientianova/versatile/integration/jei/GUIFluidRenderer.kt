package com.scientianova.versatile.integration.jei

import com.scientianova.versatile.machines.gui.GUiUtils
import mezz.jei.plugins.vanilla.ingredients.fluid.FluidStackRenderer
import net.minecraftforge.fluids.FluidStack

object GUIFluidRenderer : FluidStackRenderer(1000, false, 16, 16, null) {
    override fun render(x: Int, y: Int, stack: FluidStack?) {
        if (stack == null) return
        GUiUtils.drawFluidStack(stack, x, y, 16, 16)
    }
}