package com.emosewapixel.pixellib.machines.gui.textures.interactable

import com.emosewapixel.pixellib.machines.gui.textures.IDrawable

data class ButtonTextureGroup @JvmOverloads constructor(val on: IDrawable, val off: IDrawable, val selected: IDrawable = off) : IDrawable {
    override val uStart = 0.0
    override val vStart = 0.0
    override val uEnd = 1.0
    override val vEnd = 1.0

    override fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Double, vStart: Double, uEnd: Double, vEnd: Double) = draw(x, y, width, height, uStart, vStart, uEnd, vEnd, ButtonDrawMode.OFF)

    fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Double = 0.0, vStart: Double = 0.0, uEnd: Double = 1.0, vEnd: Double = 1.0, drawMode: ButtonDrawMode) = (when (drawMode) {
        ButtonDrawMode.OFF -> on
        ButtonDrawMode.SELECTED -> selected
        ButtonDrawMode.ON -> off
    }).let { it.draw(x, y, width, height, it.uStart * uStart, it.vStart * vStart, it.uEnd * uEnd, it.vEnd * vEnd) }
}