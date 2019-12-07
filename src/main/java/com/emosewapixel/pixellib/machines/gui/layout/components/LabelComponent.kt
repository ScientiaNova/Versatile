package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.machines.gui.layout.DefaultSizeConstants
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import net.minecraft.client.Minecraft
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.DistExecutor
import java.util.function.Supplier

open class LabelComponent(val text: String, override var x: Int, override var y: Int) : IGUIComponent {
    var location = LabelLocation.START
    var color = 0x404040
    override val height = DefaultSizeConstants.TEXT_HEIGHT
    override val width
        get() = DistExecutor.runForDist(
                { Supplier { Minecraft.getInstance().fontRenderer.getStringWidth(text) / if (location == LabelLocation.START) 1 else 2 } },
                { Supplier { text.length / if (location == LabelLocation.START) 1 else 2 } }
        )

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        val font = Minecraft.getInstance().fontRenderer
        when (location) {
            LabelLocation.CENTER -> font.drawString(text, xOffset + x - font.getStringWidth(text) / 2f, yOffset + y.toFloat(), color)
            LabelLocation.START -> font.drawString(text, xOffset + x.toFloat(), yOffset + y.toFloat(), color)
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