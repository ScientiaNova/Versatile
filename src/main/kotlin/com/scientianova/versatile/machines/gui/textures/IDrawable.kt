package com.scientianova.versatile.machines.gui.textures

interface IDrawable {
    val uStart: Float
    val vStart: Float
    val uEnd: Float
    val vEnd: Float

    fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Float = this.uStart, vStart: Float = this.vStart, uEnd: Float = this.uEnd, vEnd: Float = this.vEnd)
}