package com.scientianova.versatile.machines.gui.layout.components.still

import com.scientianovateam.versatile.machines.gui.layout.DefaultSizeConstants
import com.scientianovateam.versatile.machines.gui.layout.IGUIComponent
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
                { Supplier { Minecraft.getInstance().fontRenderer.getStringWidth(text) } },
                { Supplier { text.length * 4 } }
        )

    @OnlyIn(Dist.CLIENT)
    override fun drawInBackground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        val font = Minecraft.getInstance().fontRenderer
        when (location) {
            LabelLocation.CENTER -> font.drawString(text, xOffset + x - font.getStringWidth(text) / 2f, yOffset + y.toFloat(), color)
            LabelLocation.START -> font.drawString(text, xOffset + x.toFloat(), yOffset + y.toFloat(), color)
        }
    }

    enum class LabelLocation {
        CENTER,
        START
    }
}