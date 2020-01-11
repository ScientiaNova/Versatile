package com.scientianovateam.versatile.materialsystem.elements

import com.scientianovateam.versatile.materialsystem.lists.Elements

data class ElementStack @JvmOverloads constructor(val element: Element, var count: Int = 1) {
    val isEmpty get() = this == EMPTY || element.name == "null" || count == 0

    companion object {
        val EMPTY = Elements["null"] * 0
    }
}