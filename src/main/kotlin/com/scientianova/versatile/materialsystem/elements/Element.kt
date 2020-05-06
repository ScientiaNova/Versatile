package com.scientianova.versatile.materialsystem.elements

import com.scientianova.versatile.materialsystem.lists.addElement

open class Element(val symbol: String, val protons: Int, val neutrons: Int) {
    init {
        addElement(this)
    }

    val atomicMass get() = protons * 1.00782458 + neutrons * 1.008664

    val density get() = atomicMass / 22.414 * 1000

    operator fun times(count: Int) = ElementStack(this, count)

    override fun toString() = symbol

    @JvmOverloads
    fun toStack(count: Int = 1) = ElementStack(this, count)
}