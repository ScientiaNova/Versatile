package com.emosewapixel.pixellib.machines.gui.textures

import com.emosewapixel.pixellib.extensions.toResLoc
import com.emosewapixel.pixellib.machines.gui.GUiUtils
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

data class GUITexture @JvmOverloads constructor(val location: ResourceLocation, override val uStart: Double = 0.0, override val vStart: Double = 0.0, override val uEnd: Double = 1.0, override val vEnd: Double = 1.0) : IDrawable {
    @JvmOverloads
    constructor(location: String, xStart: Double = 0.0, yStart: Double = 0.0, xEnd: Double = 1.0, yEnd: Double = 1.0) : this(location.toResLoc(), xStart, yStart, xEnd, yEnd)

    constructor(location: String, xStart: Int, yStart: Int, width: Int, height: Int, totalWidth: Int, totalHeight: Int)
            : this(location, xStart / totalWidth.toDouble(), yStart / totalHeight.toDouble(), (xStart + width) / totalWidth.toDouble(), (yStart + height) / totalHeight.toDouble())

    @OnlyIn(Dist.CLIENT)
    override fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Double, vStart: Double, uEnd: Double, vEnd: Double) =
            GUiUtils.drawTexture(location, x, y, width, height, uStart, vStart, uEnd, vEnd)
}