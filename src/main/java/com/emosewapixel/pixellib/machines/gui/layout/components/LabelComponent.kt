package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import net.minecraft.client.Minecraft
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.DistExecutor
import java.util.function.Supplier

open class LabelComponent(val text: String, override var x: Int, override var y: Int) : IGUIComponent {
    override val tooltips = mutableListOf<String>()
    var location = LabelLocation.START
    var color = 0x404040
    override val height = 10
    override val width
        get() = DistExecutor.runForDist(
                { Supplier { Minecraft.getInstance().fontRenderer.getStringWidth(text) } },
                { Supplier { text.length } }
        )

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int) {
        val font = Minecraft.getInstance().fontRenderer
        when (location) {
            LabelLocation.CENTER -> font.drawString(text, xOffset - font.getStringWidth(text) / 2f, y.toFloat(), color)
            LabelLocation.START -> font.drawString(text, yOffset.toFloat(), y.toFloat(), color)
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun isSelected(mouseX: Double, mouseY: Double) =
            x < mouseX && mouseX < x + Minecraft.getInstance().fontRenderer.getStringWidth(text) && y < mouseY && mouseY < x + 10

    enum class LabelLocation {
        CENTER,
        START
    }
}