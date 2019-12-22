package com.scientianovateam.versatile.machines.gui.textures.updating

import com.scientianovateam.versatile.machines.gui.textures.IDrawable
import com.scientianovateam.versatile.machines.gui.textures.still.GUITexture
import net.minecraft.util.ResourceLocation

data class AnimatedGUITexture @JvmOverloads constructor(val frames: List<GUITexture>, val tickDelay: Int = 1) : IDrawable {
    @JvmOverloads
    constructor(location: ResourceLocation, frameCount: Int, tickDelay: Int = 1) : this(framesFromTexture(location, frameCount), tickDelay)

    override val uStart = 0.0
    override val vStart = 0.0
    override val uEnd = 1.0
    override val vEnd = 1.0

    private var lastTime = System.currentTimeMillis()

    var currentFrame = 0
        get() {
            if (System.currentTimeMillis() >= lastTime + tickDelay * 50) {
                lastTime = System.currentTimeMillis()
                field++
                if (field >= frames.size) field = 0
            }
            return field
        }
        private set

    override fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Double, vStart: Double, uEnd: Double, vEnd: Double) =
            draw(x, y, width, height, uStart, vStart, uEnd, vEnd, currentFrame)

    fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Double = 0.0, vStart: Double = 0.0, uEnd: Double = 1.0, vEnd: Double = 1.0, frame: Int) {
        if (frames.isEmpty()) return
        val texture = frames.getOrElse(frame) { frames[0] }
        texture.draw(x, y, width, height, texture.uStart * uStart, texture.vStart * vStart, texture.uEnd * uEnd, texture.vEnd * vEnd)
    }

    companion object {
        fun framesFromTexture(location: ResourceLocation, frameCount: Int): List<GUITexture> {
            val frameBounds = 1.0 / frameCount
            return (0 until frameCount).map { GUITexture(location, vStart = it * frameBounds, vEnd = (it + 1) * frameBounds) }
        }
    }
}