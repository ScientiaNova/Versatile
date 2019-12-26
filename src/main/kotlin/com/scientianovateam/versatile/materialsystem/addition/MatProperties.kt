package com.scientianovateam.versatile.materialsystem.addition

import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.properties.*
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.IItemTier

object MatProperties {
    @JvmStatic
    val TEXTURE_TYPE = MatProperty("versatile:texture_type".toResLoc(), ::merge) { BaseTextureTypes.REGULAR }
    @JvmStatic
    val COLOR = MatProperty("versatile:color".toResLoc(), ::merge) { -1 }
    @JvmStatic
    val TIER = MatProperty("versatile:tier".toResLoc(), ::merge) { 0 }
    @JvmStatic
    val HARVEST_TIER = MatProperty("versatile:harvest_tier".toResLoc(), ::merge) {
        it.harvestTier(1.5f * (it.tier + 1), 1.5f * (it.tier + 1))
    }
    @JvmStatic
    val ITEM_TIER = MatProperty<IItemTier?>("versatile:item_tier".toResLoc(), ::merge) { null }
    @JvmStatic
    val ARMOR_MATERIAL = MatProperty<IArmorMaterial?>("versatile:armor_material".toResLoc(), ::merge) { null }
    @JvmStatic
    val ELEMENT = MatProperty("versatile:element".toResLoc(), ::merge) { BaseElements.NULL }
    @JvmStatic
    val BURN_TIME = MatProperty("versatile:burn_time".toResLoc(), ::merge) { 0 }
    @JvmStatic
    val COMPOUND_TYPE = MatProperty("versatile:compound_type".toResLoc(), ::merge) { CompoundType.CHEMICAL }
    @JvmStatic
    val DENSITY_MULTIPLIER = MatProperty("versatile:density_multiplier".toResLoc(), ::merge) { 1f }
    @JvmStatic
    val PROCESSING_MULTIPLIER = MatProperty("versatile:processing_multiplier".toResLoc(), ::merge) { 1 }
    @JvmStatic
    val UNREFINED_COLOR = MatProperty("versatile:unrefined_color".toResLoc(), ::merge, default = Material::color)
    @JvmStatic
    val FLUID_TEMPERATURE = MatProperty("versatile:fluid_temperature".toResLoc(), ::merge) { 0 }
    @JvmStatic
    val BOILING_TEMPERATURE = MatProperty("versatile:boiling_temperature".toResLoc(), ::merge) { 0 }
    @JvmStatic
    val BOILING_MATERIAL = MatProperty("versatile:boiling_material".toResLoc(), ::merge) { it }
    @JvmStatic
    val REFINED_MATERIAL = MatProperty("versatile:refined_material".toResLoc(), ::merge) { it }
    @JvmStatic
    val PH = MatProperty("versatile:ph".toResLoc(), ::merge, { it > 0f && it < 14f }) { 7f }
    @JvmStatic
    val ALPHA = MatProperty("versatile:alpha".toResLoc(), ::merge) { 0xFF }
    @JvmStatic
    val BLOCK_COMPACTION = MatProperty("versatile:block_compation".toResLoc(), ::merge) { BlockCompaction.FROM_3X3 }
    @JvmStatic
    val TRANSITION_PROPERTIES = MatProperty<TransitionProperties?>("versatile:transition_properties".toResLoc(), ::merge) { null }
    @JvmStatic
    val HAS_ORE = MatProperty("versatile:has_ore".toResLoc(), ::merge) { false }
    @JvmStatic
    val IS_GAS = MatProperty("versatile:is_gas".toResLoc(), ::merge) { false }
    @JvmStatic
    val SIMPLE_PROCESSING = MatProperty("versatile:simple_processing".toResLoc(), ::merge) { true }
    @JvmStatic
    val ROD_OUTPUT_COUNT = MatProperty("versatile:rod_output_count".toResLoc(), ::merge) { 1 }
    @JvmStatic
    val HAS_DUST = MatProperty("versatile:has_dust".toResLoc(), ::merge) { false }
    @JvmStatic
    val DISPLAY_TYPE = MatProperty("versatile:display_type".toResLoc(), ::merge) { DisplayType.COMPOUND }
    @JvmStatic
    val MAIN_ITEM_TYPE = MatProperty<Form?>("versatile:main_item_type".toResLoc(), { first, second ->
        when (first) {
            is Form -> when (second) {
                is Form -> if (second.typePriority > first.typePriority) second else first
                else -> first
            }
            else -> second as? Form
        }
    }, { it?.itemConstructor != null }) { null }
}