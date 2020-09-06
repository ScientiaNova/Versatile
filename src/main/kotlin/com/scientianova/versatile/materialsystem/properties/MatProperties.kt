@file:JvmName("MatProperties")

package com.scientianova.versatile.materialsystem.properties

import com.scientianova.versatile.common.extensions.toResLoc
import com.scientianova.versatile.common.registry.elements
import com.scientianova.versatile.materialsystem.materials.regular
import com.scientianova.versatile.materialsystem.elements.nullElem
import com.scientianova.versatile.materialsystem.materials.Material
import com.scientianova.versatile.materialsystem.materials.MaterialStack
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.IItemTier

val associatedNames = MatProperty("versatile:associated_names".toResLoc(), List<String>::isNotEmpty) { listOf(name) }
val composition = MatProperty("versatile:composition".toResLoc()) { emptyList<MaterialStack>() }
val textureSet = MatProperty("versatile:texture_set".toResLoc()) { regular }
val color = MatProperty("versatile:color".toResLoc()) { -1 }
val tier = MatProperty("versatile:tier".toResLoc()) { 0 }
val harvestTier = MatProperty("versatile:harvest_tier".toResLoc()) {
    val tier = get(tier)
    HarvestTier(1.5f * (tier + 1), 1.5f * (tier + 1), tier)
}
val itemTier = MatProperty<IItemTier?>("versatile:item_tier".toResLoc()) { null }
val armorMat = MatProperty<IArmorMaterial?>("versatile:armor_material".toResLoc()) { null }
val element = MatProperty("versatile:element".toResLoc()) { get(associatedNames).map(elements::get).firstOrNull() ?: nullElem }
val baseBurnTime = MatProperty("versatile:burn_time".toResLoc()) { 0 }
val isAlloy = MatProperty("versatile:alloy".toResLoc()) { false }
val densityMultiplier = MatProperty("versatile:density_multiplier".toResLoc()) { 1f }
val processingMultiplier = MatProperty("versatile:processing_multiplier".toResLoc()) { 1 }
val unrefinedColor = MatProperty("versatile:unrefined_color".toResLoc(), default = color::get)
val liquidTemp = MatProperty("versatile:liquid_temperature".toResLoc()) { 0 }
val gasTemp = MatProperty("versatile:gas_temperature".toResLoc()) { 0 }
val liquidNames = MatProperty("versatile:liquid_names".toResLoc(), List<String>::isNotEmpty, associatedNames::get)
val gasNames = MatProperty("versatile:gas_names".toResLoc(), List<String>::isNotEmpty, associatedNames::get)
val liquidColor = MatProperty("versatile:liquid_color".toResLoc(), default = color::get)
val gasColor = MatProperty("versatile:gas_color".toResLoc(), default = color::get)
val refinedMat = MatProperty<Material?>("versatile:refined_material".toResLoc()) { null }
val ph = MatProperty("versatile:ph".toResLoc(), { it > 0f && it < 14f }) { 7f }
val alpha = MatProperty("versatile:alpha".toResLoc()) { 0xFF }
val blockCompaction = MatProperty("versatile:block_compaction".toResLoc()) { BlockCompaction.FROM_3X3 }
val transitionProperties = MatProperty<TransitionProperties?>("versatile:transition_properties".toResLoc()) { null }
val hasOre = MatProperty("versatile:has_ore".toResLoc()) { false }
val simpleProcessing = MatProperty("versatile:simple_processing".toResLoc()) { true }
val rodOutputCount = MatProperty("versatile:rod_output_count".toResLoc()) { 1 }
val hasDust = MatProperty("versatile:has_dust".toResLoc()) { false }
val hasIngot = MatProperty("versatile:has_ingot".toResLoc()) { false }
val hasGem = MatProperty("versatile:has_gem".toResLoc()) { false }
val chemicalGroup = MatProperty("versatile:chemical_group".toResLoc()) { false }
val malleable = MatProperty("versatile:malleable".toResLoc()) { true }