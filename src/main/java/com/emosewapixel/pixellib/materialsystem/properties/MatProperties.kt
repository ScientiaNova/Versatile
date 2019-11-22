package com.emosewapixel.pixellib.materialsystem.properties

import com.emosewapixel.pixellib.extensions.toResLoc
import com.emosewapixel.pixellib.materialsystem.addition.BaseMaterials
import com.emosewapixel.pixellib.materialsystem.elements.BaseElements
import com.emosewapixel.pixellib.materialsystem.materials.BlockCompaction
import com.emosewapixel.pixellib.materialsystem.materials.CompoundType
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.materials.TransitionProperties
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.IItemTier

object MatProperties {
    val TEXTURE_TYPE = MatProperty("pixellib:texture_type".toResLoc()) { BaseMaterials.REGULAR }
    val COLOR = MatProperty("pixellib:color".toResLoc()) { -1 }
    val TIER = MatProperty("pixellib:tier".toResLoc()) { 0 }
    val HARVEST_TIER = MatProperty("pixellib:harvest_tier".toResLoc()) {
        it.harvestTier(1.5f * (it.tier + 1), 1.5f * (it.tier + 1))
    }
    val ITEM_TIER = MatProperty<IItemTier?>("pixellib:item_tier".toResLoc()) { null }
    val ARMOR_MATERIAL = MatProperty<ArmorMaterial?>("pixellib:armor_material".toResLoc()) { null }
    val ELEMENT = MatProperty("pixellib:element".toResLoc()) { BaseElements.NULL }
    val SECOND_NAME = MatProperty("pixellib:second_name".toResLoc(), default = Material::name)
    val BURN_TIME = MatProperty("pixellib:burn_time".toResLoc()) { 0 }
    val COMPOUND_TYPE = MatProperty("pixellib:compound_type".toResLoc()) { CompoundType.CHEMICAL }
    val DENSITY_MULTIPLIER = MatProperty("pixellib:density_multiplier".toResLoc()) { 1f }
    val PROCESSING_MULTIPLIER = MatProperty("pixellib:processing_multiplier".toResLoc()) { 1 }
    val UNREFINED_COLOR = MatProperty("pixellib:unrefined_color".toResLoc(), default = Material::color)
    val FLUID_TEMPERATURE = MatProperty("pixellib:fluid_temperature".toResLoc()) { 0 }
    val BOILING_TEMPERATURE = MatProperty("pixellib:boiling_temperature".toResLoc()) { 0 }
    val BOILING_MATERIAL = MatProperty("pixellib:boiling_material".toResLoc()) { it }
    val REFINED_MATERIAL = MatProperty("pixellib:refined_material".toResLoc()) { it }
    val PH = MatProperty("pixellib:ph".toResLoc()) { 7f }
    val ALPHA = MatProperty("pixellib:alpha".toResLoc()) { 0xFF }
    val BLOCK_COMPACTION = MatProperty("pixellib:block_compation".toResLoc()) { BlockCompaction.FROM_3X3 }
    val TRANSITION_PROPERTIES = MatProperty<TransitionProperties?>("pixellib:transition_properties".toResLoc()) { null }
    val HAS_ORE = MatProperty("pixellib:has_ore".toResLoc()) { false }
    val IS_GAS = MatProperty("pixellib:is_gas".toResLoc()) { false }
    val SIMPLE_PROCESSING = MatProperty("pixellib:simple_processing".toResLoc()) { true }
    val ROD_OUTPUT_COUNT = MatProperty("pixellib:rod_output_count".toResLoc()) { 1 }
    val IS_DUST_MATERIAL = MatProperty("pixellib:is_dust_material".toResLoc()) { false }
    val IS_INGOT_MATERIAL = MatProperty("pixellib:is_ingot_material".toResLoc()) { false }
    val IS_GEM_MATERIAL = MatProperty("pixellib:is_gem_material".toResLoc()) { false }
    val IS_FLUID_MATERIAL = MatProperty("pixellib:is_fluid_material".toResLoc()) { false }
    val IS_GROUP_MATERIAL = MatProperty("pixellib:is_group_material".toResLoc()) { false }
}