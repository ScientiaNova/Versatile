package com.scientianovateam.versatile

import com.scientianovateam.versatile.common.registry.VersatileRegistry
import com.scientianovateam.versatile.materialsystem.addition.BaseMaterials
import com.scientianovateam.versatile.materialsystem.builders.ingotMaterial

@VersatileRegistry
object TestMaterialRegistry {
    @JvmField
    val ANGMALLEN = ingotMaterial("angmallen") {
        tier = 2
        color = 0xe0d78a
        composition = listOf(BaseMaterials.IRON.toStack(), BaseMaterials.GOLD.toStack())
    }
}