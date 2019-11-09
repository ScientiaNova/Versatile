package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import net.minecraft.client.Minecraft
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class LabelComponent(val text: String, override val x: Int, override val y: Int) : IGUIComponent {
    override val tooltips = mutableListOf<String>()
    var location = LabelLocation.START
    var color = 0x404040

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        val font = screen.minecraft.fontRenderer
        when (location) {
            LabelLocation.CENTER -> font.drawString(text, screen.guiLeft - font.getStringWidth(text) / 2f, y.toFloat(), color)
            LabelLocation.START -> font.drawString(text, screen.guiLeft.toFloat(), y.toFloat(), color)
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Int, mouseY: Int): Boolean {
        val font = Minecraft.getInstance().fontRenderer
        return x < mouseX && mouseX < x + font.getStringWidth(text) && y < mouseY && mouseY < x + 10
    }

    enum class LabelLocation {
        CENTER,
        START
    }
}