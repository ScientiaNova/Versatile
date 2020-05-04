package com.scientianova.versatile

import com.scientianova.versatile.materialsystem.addition.BaseMaterials
import com.scientianova.versatile.materialsystem.builders.ingotMaterial

object TestMaterialRegistry {
    @JvmField
    val ANGMALLEN = ingotMaterial("angmallen") {
        tier = 2
        color = 0xe0d78a
        composition = listOf(BaseMaterials.IRON.toStack(), BaseMaterials.GOLD.toStack())
    }
}