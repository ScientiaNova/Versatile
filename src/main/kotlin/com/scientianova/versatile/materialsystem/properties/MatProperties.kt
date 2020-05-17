@file:JvmName("MatProperties")

package com.scientianova.versatile.materialsystem.properties

import com.scientianova.versatile.common.extensions.toResLoc
import com.scientianova.versatile.common.registry.ELEMENTS
import com.scientianova.versatile.materialsystem.materials.REGULAR
import com.scientianova.versatile.materialsystem.elements.NULL
import com.scientianova.versatile.materialsystem.materials.Material
import com.scientianova.versatile.materialsystem.materials.MaterialStack
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.IItemTier

val ASSOCIATED_NAMES = MatProperty("versatile:associated_names".toResLoc(), List<String>::isNotEmpty) { emptyList() }
val COMPOSITION = MatProperty("versatile:composition".toResLoc()) { emptyList<MaterialStack>() }
val TEXTURE_SET = MatProperty("versatile:texture_set".toResLoc()) { REGULAR }
val COLOR = MatProperty("versatile:color".toResLoc()) { -1 }
val TIER = MatProperty("versatile:tier".toResLoc()) { 0 }
val HARVEST_TIER = MatProperty("versatile:harvest_tier".toResLoc()) {
    harvestTier(1.5f * (tier + 1), 1.5f * (tier + 1))
}
val ITEM_TIER = MatProperty<IItemTier?>("versatile:item_tier".toResLoc()) { null }
val ARMOR_MATERIAL = MatProperty<IArmorMaterial?>("versatile:armor_material".toResLoc()) { null }
val ELEMENT = MatProperty("versatile:element".toResLoc()) { associatedNames.map(ELEMENTS::get).firstOrNull() ?: NULL }
val BASE_BURN_TIME = MatProperty("versatile:burn_time".toResLoc()) { 0 }
val COMPOUND_TYPE = MatProperty("versatile:compound_type".toResLoc()) { CompoundType.CHEMICAL }
val DENSITY_MULTIPLIER = MatProperty("versatile:density_multiplier".toResLoc()) { 1f }
val PROCESSING_MULTIPLIER = MatProperty("versatile:processing_multiplier".toResLoc()) { 1 }
val UNREFINED_COLOR = MatProperty("versatile:unrefined_color".toResLoc(), defaultFun = Material::color)
val LIQUID_TEMPERATURE = MatProperty("versatile:liquid_temperature".toResLoc()) { 0 }
val GAS_TEMPERATURE = MatProperty("versatile:gas_temperature".toResLoc()) { 0 }
val LIQUID_NAMES = MatProperty("versatile:liquid_names".toResLoc(), List<String>::isNotEmpty) { associatedNames }
val GAS_NAMES = MatProperty("versatile:gas_names".toResLoc(), List<String>::isNotEmpty) { associatedNames }
val GAS_COLOR = MatProperty("versatile:gas_color".toResLoc()) { color }
val REFINED_MATERIAL = MatProperty<Material?>("versatile:refined_material".toResLoc()) { null }
val PH = MatProperty("versatile:ph".toResLoc(), { it > 0f && it < 14f }) { 7f }
val ALPHA = MatProperty("versatile:alpha".toResLoc()) { 0xFF }
val BLOCK_COMPACTION = MatProperty("versatile:block_compaction".toResLoc()) { BlockCompaction.FROM_3X3 }
val TRANSITION_PROPERTIES = MatProperty<TransitionProperties?>("versatile:transition_properties".toResLoc()) { null }
val HAS_ORE = MatProperty("versatile:has_ore".toResLoc()) { false }
val SIMPLE_PROCESSING = MatProperty("versatile:simple_processing".toResLoc()) { true }
val ROD_OUTPUT_COUNT = MatProperty("versatile:rod_output_count".toResLoc()) { 1 }
val HAS_DUST = MatProperty("versatile:has_dust".toResLoc()) { false }
val HAS_INGOT = MatProperty("versatile:has_ingot".toResLoc()) { false }
val HAS_GEM = MatProperty("versatile:has_gem".toResLoc()) { false }
val DISPLAY_TYPE = MatProperty("versatile:display_type".toResLoc()) { DisplayType.COMPOUND }
val MALLEABLE = MatProperty("versatile:malleable".toResLoc()) { true }