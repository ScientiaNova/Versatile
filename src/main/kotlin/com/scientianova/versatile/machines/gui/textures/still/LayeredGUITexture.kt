package com.scientianova.versatile.machines.gui.textures.still

import com.scientianovateam.versatile.machines.gui.textures.IDrawable

class LayeredGUITexture(firstTexture: GUITexture, vararg rest: GUITexture) : IDrawable {
    val textures = listOf(firstTexture, *rest)

    override val uStart = 0.0
    override val vStart = 0.0
    override val uEnd = 1.0
    override val vEnd = 1.0

    override fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Double, vStart: Double, uEnd: Double, vEnd: Double) {
        textures.forEach { it.draw(x, y, width, height, uStart, vStart, uEnd, vEnd) }
    }
}