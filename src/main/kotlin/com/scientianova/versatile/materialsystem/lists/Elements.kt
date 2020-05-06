@file:JvmName("Elements")

package com.scientianova.versatile.materialsystem.lists

import com.scientianova.versatile.materialsystem.elements.Element

private val elements = hashMapOf<String, Element>()

val allElements get() = elements.values

fun addElement(element: Element) {
    elements[element.symbol.toLowerCase()] = element
}

fun elementFor(name: String) = elements[name]

fun elementExists(name: String) = name in elements