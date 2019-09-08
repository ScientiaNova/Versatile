package com.emosewapixel.pixellib.materialsystem.materials.utility

import com.emosewapixel.pixellib.materialsystem.materials.Material

//Material Stacks are ways of getting an amount of a certain Material
data class MaterialStack @JvmOverloads constructor(val material: Material, var count: Int = 1) {
    val isEmpty: Boolean
        get() = count == 0
}