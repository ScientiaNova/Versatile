package com.emosewapixel.pixellib.materialsystem.lists

import com.emosewapixel.pixellib.materialsystem.elements.Element

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
    operator fun get(name: String) = elements[name]

    @JvmStatic
    operator fun contains(name: String) = name in elements
}