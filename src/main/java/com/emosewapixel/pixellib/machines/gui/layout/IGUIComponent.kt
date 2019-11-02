package com.emosewapixel.pixellib.machines.gui.layout

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

interface IGUIComponent {
    val x: Int
    val y: Int
    val tooltips: MutableList<String>

    @OnlyIn(Dist.CLIENT)
    fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean = false

    @OnlyIn(Dist.CLIENT)
    fun onMouseDragged(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean = false

    @OnlyIn(Dist.CLIENT)
    fun onMouseReleased(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean = false

    @OnlyIn(Dist.CLIENT)
    fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen)

    @OnlyIn(Dist.CLIENT)
    fun drawInForeground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        if (tooltips.isNotEmpty() && isSelected(mouseX - screen.guiLeft, mouseY - screen.guiTop))
            screen.renderTooltip(tooltips, mouseX, mouseY)
    }

    @OnlyIn(Dist.CLIENT)
    fun isSelected(mouseX: Int, mouseY: Int): Boolean
}