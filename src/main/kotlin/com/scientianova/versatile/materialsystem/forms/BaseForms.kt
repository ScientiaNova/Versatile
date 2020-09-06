@file:JvmName("BaseForms")

package com.scientianova.versatile.materialsystem.forms

import com.scientianova.versatile.common.extensions.json
import com.scientianova.versatile.common.extensions.toResLoc
import com.scientianova.versatile.fluids.ExtendedFluidAttributes
import com.scientianova.versatile.materialsystem.events.DeferredFormRegister
import com.scientianova.versatile.materialsystem.properties.*
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.LanguageMap
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.ForgeFlowingFluid

internal val formReg = DeferredFormRegister()

val dustForm by formReg.item("dust") {
    set(matPredicate) { hasDust::get }
    set(bucketVolume) { 144 }
}
val gemForm by formReg.item("gem") {
    set(matPredicate) { hasGem::get }
    set(bucketVolume) { 144 }
}
val ingotForm by formReg.item("ingot") {
    set(matPredicate) { hasIngot::get }
    set(bucketVolume) { 144 }
}
val nuggetForm by formReg.item("nugget") {
    set(matPredicate) { { it[hasIngot] && it[malleable] } }
    set(bucketVolume) { 16 }
}
val blockForm by formReg.block("storage_block") {
    set(matPredicate) { { it[blockCompaction] != BlockCompaction.NONE } }
    set(registryName) { ResourceLocation("versatile:${mat}_block") }
    set(burnTime) { mat[baseBurnTime] * 10 }
    set(bucketVolume) { 1296 }
}
val oreForm by formReg.block("ore") {
    set(matPredicate) { hasOre::get }
    set(formColor) { mat[unrefinedColor] }
    set(indexBlacklist) { listOf(1) }
    set(bucketVolume) { 144 }
    set(burnTime) { 0 }
}
val liquidForm by formReg.fluid("liquid") {
    set(matPredicate) { { it[liquidTemp] > 0 } }
    set(fluidProperties) {
        ForgeFlowingFluid.Properties({ get(stillFluid)!! }, { get(flowingFluid)!! }, ExtendedFluidAttributes.Builder(
                "minecraft:block/water_still".toResLoc(),
                "minecraft:block/water_flow".toResLoc(),
                color = if (mat[liquidColor] !in 0xffffffff.toInt()..0x1000000) mat[liquidColor] else mat[liquidColor] or 0xff000000.toInt(),
                temperature = mat[liquidTemp],
                isGas = false,
                localizedNameFun = {
                    if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey)
                    else TranslationTextComponent("material.${mat[liquidNames].first()}")
                }
        )).block { get(block) as FlowingFluidBlock }.bucket { get(item)!! }
    }
    set(itemModel) {
        json {
            "parent" to "versatile:item/materialitems/fluid_bucket"
        }
    }
    set(formColor) { mat[liquidColor] }
    set(registryName) { ResourceLocation("versatile:${mat[liquidNames].first()}") }
    set(combinedFluidTags) { mat[liquidNames].map { "forge:$it" } }
}

val gasForm by formReg.fluid("gas") {
    set(matPredicate) { { it[gasTemp] > 0 } }
    set(fluidProperties) {
        ForgeFlowingFluid.Properties({ get(stillFluid)!! }, { get(flowingFluid)!! }, ExtendedFluidAttributes.Builder(
                "minecraft:block/water_still".toResLoc(),
                "minecraft:block/water_flow".toResLoc(),
                color = if (mat[gasColor] !in 0xffffffff.toInt()..0x1000000) mat[gasColor] else mat[gasColor] or 0xff000000.toInt(),
                temperature = mat[gasTemp],
                isGas = true,
                localizedNameFun = {
                    if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey)
                    else TranslationTextComponent("material.${mat[gasNames].first()}")
                }
        )).block { get(block) as FlowingFluidBlock }.bucket { get(item)!! }
    }
    set(itemModel) {
        json {
            "parent" to "versatile:item/materialitems/fluid_gas_bucket"
        }
    }
    set(formColor) { mat[gasColor] }
    set(registryName) { ResourceLocation("versatile:${mat[gasNames][0]}") }
    set(combinedItemTags) { mat[gasNames].map { "forge:buckets/$it" } }
    set(combinedFluidTags) { mat[gasNames].map { "forge:$it" } }
}