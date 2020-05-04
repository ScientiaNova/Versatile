@file:JvmName("BaseForms")

package com.scientianova.versatile.materialsystem.addition

import com.scientianova.versatile.materialsystem.builders.blockForm
import com.scientianova.versatile.materialsystem.builders.fluidForm
import com.scientianova.versatile.materialsystem.builders.itemForm
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.properties.BlockCompaction
import net.minecraft.util.ResourceLocation

val DUST_FORM = itemForm("dust") {
    generate = Material::hasDust
    bucketVolume = 144
}
val GEM_FORM = itemForm("gem") {
    generate = Material::hasGem
    bucketVolume = 144
}
val INGOT_FORM = itemForm("ingot") {
    generate = Material::hasIngot
    bucketVolume = 144
}
val NUGGET_FORM = itemForm("nugget") {
    generate = { it.hasIngot && it.malleable }
    bucketVolume = 16
}
val BLOCK_FORM = blockForm("storage_block") {
    generate = { it.blockCompaction != BlockCompaction.NONE }
    REGISTRY_NAME { ResourceLocation("versatile:${mat}_block") }
    BURN_TIME { mat.standardBurnTime * 10 }
    bucketVolume = 1296
}
val ORE_FORM = blockForm("ore") {
    generate = { it.hasOre }
    FORM_COLOR { mat.unrefinedColor }
    indexBlacklist = listOf(1)
    bucketVolume = 144
    BURN_TIME { 0 }
}
val LIQUID_FORM = fluidForm("liquid") {
    generate = { it.liquidTemperature > 0 }
    REGISTRY_NAME { ResourceLocation("versatile:${mat.liquidNames[0]}") }
    COMBINED_FLUID_TAGS { mat.associatedNames.map { "forge:${mat.liquidNames}" } }
}

val GAS_FORM = fluidForm("gas") {
    generate = { it.gasTemperature > 0 }
    REGISTRY_NAME { ResourceLocation("versatile:${mat.gasNames[0]}") }
    COMBINED_FLUID_TAGS { mat.associatedNames.map { "forge:${mat.gasNames}" } }
}