package com.emosewapixel.pixellib.materialsystem.elements

import com.emosewapixel.pixellib.materialsystem.addition.BaseElements

data class ElementStack @JvmOverloads constructor(val element: Element, var count: Int = 1) {
    val isEmpty get() = this == EMPTY || element == BaseElements.NULL || count == 0

    companion object {
        val EMPTY = BaseElements.NULL * 0
    }
}