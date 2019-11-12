package com.emosewapixel.pixellib.machines.gui.textures

data class ProgressBar(val background: GUITexture, val fill: GUITexture) : IRenderable {
    override val uStart = background.uStart
    override val vStart = background.vStart
    override val uEnd = background.uEnd
    override val vEnd = background.vEnd

    fun render(x: Int, y: Int, width: Int, height: Int, progress: Double, direction: Direction2D = Direction2D.RIGHT) {
        background.render(x, y, width, height)
        val current = (progress * when (direction) {
            Direction2D.RIGHT, Direction2D.LEFT -> width
            Direction2D.UP, Direction2D.DOWN -> height
        }).toInt()
        when (direction) {
            Direction2D.UP -> fill.render(x, y + height - current, width, current, vStart = current / height.toDouble())
            Direction2D.DOWN -> fill.render(x, y, width, current, vEnd = current / height.toDouble())
            Direction2D.LEFT -> fill.render(x + width - current, y, current, height, uStart = current / width.toDouble())
            Direction2D.RIGHT -> fill.render(x, y, current, height, uEnd = current / width.toDouble())
        }
    }

    override fun render(x: Int, y: Int, width: Int, height: Int, uStart: Double, vStart: Double, uEnd: Double, vEnd: Double) {
        render(x, y, width, height, 1.0)
    }
}