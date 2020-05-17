@file:JvmName("BaseForms")

package com.scientianova.versatile.materialsystem.forms

import com.scientianova.versatile.common.extensions.json
import com.scientianova.versatile.common.extensions.toResLoc
import com.scientianova.versatile.fluids.ExtendedFluidAttributes
import com.scientianova.versatile.materialsystem.events.DeferredFormRegister
import com.scientianova.versatile.materialsystem.materials.Material
import com.scientianova.versatile.materialsystem.properties.*
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.ForgeFlowingFluid

internal val formReg = DeferredFormRegister()

val DUST_FORM by formReg.item("dust") {
    generate = Material::hasDust
    bucketVolume = 144
}
val GEM_FORM by formReg.item("gem") {
    generate = Material::hasGem
    bucketVolume = 144
}
val INGOT_FORM by formReg.item("ingot") {
    generate = Material::hasIngot
    bucketVolume = 144
}
val NUGGET_FORM by formReg.item("nugget") {
    generate = { it.hasIngot && it.malleable }
    bucketVolume = 16
}
val BLOCK_FORM by formReg.block("storage_block") {
    generate = { it.blockCompaction != BlockCompaction.NONE }
    REGISTRY_NAME { ResourceLocation("versatile:${mat}_block") }
    BURN_TIME { mat.standardBurnTime * 10 }
    bucketVolume = 1296
}
val ORE_FORM by formReg.block("ore") {
    generate = { it.hasOre }
    FORM_COLOR { mat.unrefinedColor }
    indexBlacklist = listOf(1)
    bucketVolume = 144
    BURN_TIME { 0 }
}
val LIQUID_FORM by formReg.fluid("liquid") {
    generate = { it.liquidTemperature > 0 }
    FLUID_PROPERTIES {
        ForgeFlowingFluid.Properties({ stillFluid!! }, { flowingFluid!! }, ExtendedFluidAttributes.Builder(
                "minecraft:block/water_still".toResLoc(),
                "minecraft:block/water_flow".toResLoc(),
                temperature = mat.liquidTemperature,
                isGas = false
        )).block { block as FlowingFluidBlock }.bucket { item!! }
    }
    ITEM_MODEL {
        json {
            "parent" to "versatile:item/materialitems/fluid_bucket"
        }
    }
    REGISTRY_NAME { ResourceLocation("versatile:${mat.liquidNames[0]}") }
    COMBINED_FLUID_TAGS { mat.liquidNames.map { "forge:$it" } }
}

val GAS_FORM by formReg.fluid("gas") {
    generate = { it.gasTemperature > 0 }
    FLUID_PROPERTIES {
        ForgeFlowingFluid.Properties({ stillFluid!! }, { flowingFluid!! }, ExtendedFluidAttributes.Builder(
                "minecraft:block/water_still".toResLoc(),
                "minecraft:block/water_flow".toResLoc(),
                temperature = mat.gasTemperature,
                isGas = true
        )).block { block as FlowingFluidBlock }.bucket { item!! }
    }
    ITEM_MODEL {
        json {
            "parent" to "versatile:item/materialitems/fluid_gas_bucket"
        }
    }
    REGISTRY_NAME { ResourceLocation("versatile:${mat.gasNames[0]}") }
    COMBINED_FLUID_TAGS { mat.gasNames.map { "forge:$it" } }
}