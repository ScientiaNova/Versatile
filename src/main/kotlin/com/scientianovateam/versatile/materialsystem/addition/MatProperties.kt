package com.scientianovateam.versatile.materialsystem.addition

import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.properties.*
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.IItemTier

object MatProperties {
    @JvmStatic
    val TEXTURE_TYPE = MatLegacyProperty("versatile:texture_type".toResLoc(), ::merge) { BaseTextureTypes.REGULAR }
    @JvmStatic
    val COLOR = MatLegacyProperty("versatile:color".toResLoc(), ::merge) { -1 }
    @JvmStatic
    val TIER = MatLegacyProperty("versatile:tier".toResLoc(), ::merge) { 0 }
    @JvmStatic
    val HARVEST_TIER = MatLegacyProperty("versatile:harvest_tier".toResLoc(), ::merge) {
        it.harvestTier(1.5f * (it.tier + 1), 1.5f * (it.tier + 1))
    }
    @JvmStatic
    val ITEM_TIER = MatLegacyProperty<IItemTier?>("versatile:item_tier".toResLoc(), ::merge) { null }
    @JvmStatic
    val ARMOR_MATERIAL = MatLegacyProperty<IArmorMaterial?>("versatile:armor_material".toResLoc(), ::merge) { null }
    @JvmStatic
    val ELEMENT = MatLegacyProperty("versatile:element".toResLoc(), ::merge) { BaseElements.NULL }
    @JvmStatic
    val BURN_TIME = MatLegacyProperty("versatile:burn_time".toResLoc(), ::merge) { 0 }
    @JvmStatic
    val COMPOUND_TYPE = MatLegacyProperty("versatile:compound_type".toResLoc(), ::merge) { CompoundType.CHEMICAL }
    @JvmStatic
    val DENSITY_MULTIPLIER = MatLegacyProperty("versatile:density_multiplier".toResLoc(), ::merge) { 1f }
    @JvmStatic
    val PROCESSING_MULTIPLIER = MatLegacyProperty("versatile:processing_multiplier".toResLoc(), ::merge) { 1 }
    @JvmStatic
    val UNREFINED_COLOR = MatLegacyProperty("versatile:unrefined_color".toResLoc(), ::merge, default = Material::color)
    @JvmStatic
    val FLUID_TEMPERATURE = MatLegacyProperty("versatile:fluid_temperature".toResLoc(), ::merge) { 0 }
    @JvmStatic
    val BOILING_TEMPERATURE = MatLegacyProperty("versatile:boiling_temperature".toResLoc(), ::merge) { 0 }
    @JvmStatic
    val BOILING_MATERIAL = MatLegacyProperty("versatile:boiling_material".toResLoc(), ::merge) { it }
    @JvmStatic
    val REFINED_MATERIAL = MatLegacyProperty("versatile:refined_material".toResLoc(), ::merge) { it }
    @JvmStatic
    val PH = MatLegacyProperty("versatile:ph".toResLoc(), ::merge, { it > 0f && it < 14f }) { 7f }
    @JvmStatic
    val ALPHA = MatLegacyProperty("versatile:alpha".toResLoc(), ::merge) { 0xFF }
    @JvmStatic
    val BLOCK_COMPACTION = MatLegacyProperty("versatile:block_compation".toResLoc(), ::merge) { BlockCompaction.FROM_3X3 }
    @JvmStatic
    val TRANSITION_PROPERTIES = MatLegacyProperty<TransitionProperties?>("versatile:transition_properties".toResLoc(), ::merge) { null }
    @JvmStatic
    val HAS_ORE = MatLegacyProperty("versatile:has_ore".toResLoc(), ::merge) { false }
    @JvmStatic
    val IS_GAS = MatLegacyProperty("versatile:is_gas".toResLoc(), ::merge) { false }
    @JvmStatic
    val SIMPLE_PROCESSING = MatLegacyProperty("versatile:simple_processing".toResLoc(), ::merge) { true }
    @JvmStatic
    val ROD_OUTPUT_COUNT = MatLegacyProperty("versatile:rod_output_count".toResLoc(), ::merge) { 1 }
    @JvmStatic
    val HAS_DUST = MatLegacyProperty("versatile:has_dust".toResLoc(), ::merge) { false }
    @JvmStatic
    val DISPLAY_TYPE = MatLegacyProperty("versatile:display_type".toResLoc(), ::merge) { DisplayType.COMPOUND }
    @JvmStatic
    val MAIN_ITEM_TYPE = MatLegacyProperty<Form?>("versatile:main_item_type".toResLoc(), { first, second ->
        when (first) {
            is Form -> when (second) {
                is Form -> if (second.typePriority > first.typePriority) second else first
                else -> first
            }
            else -> second as? Form
        }
    }, { it?.itemConstructor != null }) { null }
}