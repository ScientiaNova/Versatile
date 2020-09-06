package com.scientianova.versatile.materialsystem.elements

data class ElementStack @JvmOverloads constructor(val element: Element, var count: Int = 1) {
    val isEmpty get() = this == EMPTY || element == nullElem || count == 0

    companion object {
        val EMPTY = nullElem * 0
    }
}