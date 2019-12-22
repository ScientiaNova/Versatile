package com.emosewapixel.pixellib.materialsystem.elements

import com.emosewapixel.pixellib.materialsystem.lists.Elements

/*
Elements are objects with a few properties, that can be assigned to materials. They're mainly meant to be used for tech mods.
If you're adding materials such as Copper, Tin, Lead, etc. please do assign them, but if you're adding fantasy materials, you may ignore them.
*/
open class Element(val symbol: String, val protons: Int, val neutrons: Int) {
    init {
        Elements.add(this)
    }

    val atomicMass get() = protons * 1.00782458 + neutrons * 1.008664

    val density get() = atomicMass / 22.414 * 1000

    operator fun times(count: Int) = ElementStack(this, count)

    override fun toString() = symbol

    @JvmOverloads
    fun toStack(count: Int = 1) = ElementStack(this, count)
}