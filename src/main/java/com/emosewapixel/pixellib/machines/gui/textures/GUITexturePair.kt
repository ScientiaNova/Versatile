package com.emosewapixel.pixellib.machines.gui.textures

data class GUITexturePair(val first: GUITexture, val second: GUITexture) : IRenderable {
    override val uStart = 0.0
    override val vStart = 0.0
    override val uEnd = 1.0
    override val vEnd = 1.0

    override fun render(x: Int, y: Int, width: Int, height: Int, uStart: Double, vStart: Double, uEnd: Double, vEnd: Double) = render(x, y, width, height, uStart, vStart, uEnd, vEnd, true)

    fun render(x: Int, y: Int, width: Int, height: Int, uStart: Double = 0.0, vStart: Double = 0.0, uEnd: Double = 1.0, vEnd: Double = 1.0, drawFirst: Boolean) = if (drawFirst)
        first.render(x, y, width, height, first.uStart * uStart, first.vStart * vStart, first.uEnd * uEnd, first.vEnd * vEnd)
    else
        second.render(x, y, width, height, second.uStart * uStart, second.vStart * vStart, second.uEnd * uEnd, second.vEnd * vEnd)
}