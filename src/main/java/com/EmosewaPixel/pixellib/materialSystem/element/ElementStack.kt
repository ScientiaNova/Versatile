package com.EmosewaPixel.pixellib.materialsystem.element

//Elements Stacks are ways of getting an amount of a certain Element
data class ElementStack @JvmOverloads constructor(val element: Element, var count: Int = 1) {
    val isEmpty: Boolean
        get() = count == 0
}