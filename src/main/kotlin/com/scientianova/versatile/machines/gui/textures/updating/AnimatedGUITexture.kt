package com.scientianova.versatile.machines.gui.textures.updating

import com.scientianova.versatile.machines.gui.textures.IDrawable
import com.scientianova.versatile.machines.gui.textures.still.GUITexture
import net.minecraft.util.ResourceLocation

data class AnimatedGUITexture @JvmOverloads constructor(val frames: List<GUITexture>, val tickDelay: Int = 1) : IDrawable {
    @JvmOverloads
    constructor(location: ResourceLocation, frameCount: Int, tickDelay: Int = 1) : this(framesFromTexture(location, frameCount), tickDelay)

    override val uStart = 0f
    override val vStart = 0f
    override val uEnd = 1f
    override val vEnd = 1f

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

    override fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Float, vStart: Float, uEnd: Float, vEnd: Float) =
            draw(x, y, width, height, uStart, vStart, uEnd, vEnd, currentFrame)

    fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Float = 0f, vStart: Float = 0f, uEnd: Float = 1f, vEnd: Float = 1f, frame: Int) {
        if (frames.isEmpty()) return
        val texture = frames.getOrElse(frame) { frames[0] }
        texture.draw(x, y, width, height, texture.uStart * uStart, texture.vStart * vStart, texture.uEnd * uEnd, texture.vEnd * vEnd)
    }

    companion object {
        fun framesFromTexture(location: ResourceLocation, frameCount: Int): List<GUITexture> {
            val frameBounds = 1f / frameCount
            return (0 until frameCount).map { GUITexture(location, vStart = it * frameBounds, vEnd = (it + 1) * frameBounds) }
        }
    }
}