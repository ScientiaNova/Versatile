package com.emosewapixel.pixellib.machines.gui.textures

interface IRenderable {
    val uStart: Double
    val vStart: Double
    val uEnd: Double
    val vEnd: Double

    fun render(x: Int, y: Int, width: Int, height: Int, uStart: Double = this.uStart, vStart: Double = this.vStart, uEnd: Double = this.uEnd, vEnd: Double = this.vEnd)
}