package com.scientianovateam.versatile.machines.gui.textures.interactable

import com.scientianovateam.versatile.machines.gui.textures.IDrawable

data class ButtonTextureGroup @JvmOverloads constructor(val on: IDrawable, val off: IDrawable, val selected: IDrawable = off) : IDrawable {
    override val uStart = 0f
    override val vStart = 0f
    override val uEnd = 1f
    override val vEnd = 1f

    override fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Float, vStart: Float, uEnd: Float, vEnd: Float) = draw(x, y, width, height, uStart, vStart, uEnd, vEnd, ButtonDrawMode.OFF)

    fun draw(x: Int, y: Int, width: Int, height: Int, uStart: Float = 0f, vStart: Float = 0f, uEnd: Float = 1f, vEnd: Float = 1f, drawMode: ButtonDrawMode) = (when (drawMode) {
        ButtonDrawMode.OFF -> on
        ButtonDrawMode.SELECTED -> selected
        ButtonDrawMode.ON -> off
    }).let { it.draw(x, y, width, height, it.uStart * uStart, it.vStart * vStart, it.uEnd * uEnd, it.vEnd * vEnd) }
}