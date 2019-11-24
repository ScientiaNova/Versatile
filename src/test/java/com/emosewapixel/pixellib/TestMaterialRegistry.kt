package com.emosewapixel.pixellib

import com.emosewapixel.pixellib.materialsystem.addition.BaseMaterials
import com.emosewapixel.pixellib.materialsystem.addition.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.addition.ingotMaterial

@MaterialRegistry
object TestMaterialRegistry {
    @JvmField
    val ANGMALLEN = ingotMaterial("angmallen") {
        tier = 2
        color = 0xe0d78a
        composition = listOf(BaseMaterials.IRON.toStack(), BaseMaterials.GOLD.toStack())
    }
}