package com.emosewapixel.pixellib.machines.gui.textures.updating

import com.emosewapixel.pixellib.machines.gui.textures.IDrawable
import com.emosewapixel.pixellib.machines.gui.textures.still.GUITexture

data class ProgressBar(val background: GUITexture, val fill: GUITexture) : IDrawable {
    override val uStart = background.uStart
    override val vStart = background.vStart
    override val uEnd = background.uEnd
    override val vEnd = background.vEnd

    fun draw(x: Int, y: Int, width: Int, height: Int, progress: Double, direction: Direction2D = Direction2D.RIGHT) {
        background.draw(x, y, width, height)
        val current = (progress * when (direction) {
            Direction2D.RIGHT, Direction2D.LEFT -> width
            Direction2D.UP, Direction2D.DOWN -> height
        }).toInt()
        when (direction) {
            Direction2D.UP -> fill.draw(x, y + height - current, width, current, vStart = current / height.toDouble())
            Direction2D.DOWN -> fill.draw(x, y, width, current, vEnd = current / height.toDouble())
            Direction2D.LEFT -> fill.draw(x + width - current, y, current, height, uStart = current / width.toDouble())
            Direction2D.RIGHT -> fill.draw(x, y, current, height, uEnd = current / width.toDouble())
        }
    }

    override fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Double, vStart: Double, uEnd: Double, vEnd: Double) {
        draw(x, y, width, height, 1.0)
    }
}