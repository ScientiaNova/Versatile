package com.scientianovateam.versatile.materialsystem.lists

import com.scientianovateam.versatile.materialsystem.elements.Element

object Elements {
    private val elements = hashMapOf<String, Element>()

    @JvmStatic
    val all
        get() = elements.values

    @JvmStatic
    fun add(element: Element) {
        elements[element.symbol.toLowerCase()] = element
    }

    @JvmStatic
    operator fun get(name: String) = elements[name] ?: error("No element with name $name")

    @JvmStatic
    operator fun contains(name: String) = name in elements
}