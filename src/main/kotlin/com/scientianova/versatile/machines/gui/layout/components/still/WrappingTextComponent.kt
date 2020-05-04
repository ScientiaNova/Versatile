package com.scientianova.versatile.machines.gui.layout.components.still

import com.scientianova.versatile.machines.gui.layout.DefaultSizeConstants
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.DistExecutor
import java.util.function.Supplier

open class WrappingTextComponent(text: String, x: Int, y: Int, override val width: Int) : LabelComponent(text, x, y) {
    override val height
        get() = DistExecutor.runForDist(
                { Supplier { DefaultSizeConstants.TEXT_HEIGHT + (Minecraft.getInstance().fontRenderer.wrapFormattedStringToWidth(text, width).split('\n').size - 1) * (DefaultSizeConstants.TEXT_HEIGHT + 1) } },
                { Supplier { text.length * 4 } }
        )

    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        val font = Minecraft.getInstance().fontRenderer
        val lines = font.wrapFormattedStringToWidth(text, width).split('\n')
        when (location) {
            LabelLocation.CENTER -> lines.forEach {
                font.drawString(it, xOffset + x - font.getStringWidth(text) / 2f, yOffset + y.toFloat(), color)
            }
            LabelLocation.START -> lines.forEach {
                font.drawString(it, xOffset + x.toFloat(), yOffset + y.toFloat(), color)
            }
        }
    }
}