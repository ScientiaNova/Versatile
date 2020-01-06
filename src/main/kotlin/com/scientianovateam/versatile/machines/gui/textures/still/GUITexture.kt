package com.scientianovateam.versatile.machines.gui.textures.still

import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.machines.gui.GUiUtils
import com.scientianovateam.versatile.machines.gui.textures.IDrawable
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

data class GUITexture @JvmOverloads constructor(val location: ResourceLocation, override val uStart: Float = 0f, override val vStart: Float = 0f, override val uEnd: Float = 1f, override val vEnd: Float = 1f) : IDrawable {
    @JvmOverloads
    constructor(location: String, xStart: Float = 0f, yStart: Float = 0f, xEnd: Float = 1f, yEnd: Float = 1f) : this(location.toResLoc(), xStart, yStart, xEnd, yEnd)

    constructor(location: String, xStart: Int, yStart: Int, width: Int, height: Int, totalWidth: Int, totalHeight: Int)
            : this(location, xStart / totalWidth.toFloat(), yStart / totalHeight.toFloat(), (xStart + width) / totalWidth.toFloat(), (yStart + height) / totalHeight.toFloat())

    @OnlyIn(Dist.CLIENT)
    override fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Float, vStart: Float, uEnd: Float, vEnd: Float) =
            GUiUtils.drawTexture(location, x, y, width, height, uStart, vStart, uEnd, vEnd)
}