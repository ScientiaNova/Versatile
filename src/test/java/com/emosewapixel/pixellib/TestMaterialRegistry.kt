package com.emosewapixel.pixellib

import com.emosewapixel.pixellib.materialsystem.addition.BaseMaterials
import com.emosewapixel.pixellib.materialsystem.addition.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.addition.ingotMaterial

@MaterialRegistry
object TestMaterialRegistry {
    val ANGMALLEN = ingotMaterial("angmallen", BaseMaterials.REGULAR, 0xe0d78a, 2) {
        composition = listOf(BaseMaterials.IRON.toStack(), BaseMaterials.GOLD.toStack())
    }
}