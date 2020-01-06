package com.scientianovateam.versatile.machines.gui.textures.still

import com.scientianovateam.versatile.machines.gui.textures.IDrawable

class LayeredGUITexture(firstTexture: GUITexture, vararg rest: GUITexture) : IDrawable {
    val textures = listOf(firstTexture, *rest)

    override val uStart = 0f
    override val vStart = 0f
    override val uEnd = 1f
    override val vEnd = 1f

    override fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Float, vStart: Float, uEnd: Float, vEnd: Float) {
        textures.forEach { it.draw(x, y, width, height, uStart, vStart, uEnd, vEnd) }
    }
}