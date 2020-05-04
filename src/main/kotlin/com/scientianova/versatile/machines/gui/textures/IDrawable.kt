package com.scientianova.versatile.machines.gui.textures

interface IDrawable {
    val uStart: Double
    val vStart: Double
    val uEnd: Double
    val vEnd: Double

    fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Double = this.uStart, vStart: Double = this.vStart, uEnd: Double = this.uEnd, vEnd: Double = this.vEnd)
}