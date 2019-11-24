package com.emosewapixel.pixellib.materialsystem.properties

import com.emosewapixel.pixellib.extensions.toResLoc
import com.emosewapixel.pixellib.materialsystem.addition.BaseTextureTypes
import com.emosewapixel.pixellib.materialsystem.elements.BaseElements
import com.emosewapixel.pixellib.materialsystem.main.Material
import com.emosewapixel.pixellib.materialsystem.main.ObjectType
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.IItemTier

object MatProperties {
    @JvmStatic
    val TEXTURE_TYPE = MatProperty("pixellib:texture_type".toResLoc(), ::merge) { BaseTextureTypes.REGULAR }
    @JvmStatic
    val COLOR = MatProperty("pixellib:color".toResLoc(), ::merge) { -1 }
    @JvmStatic
    val TIER = MatProperty("pixellib:tier".toResLoc(), ::merge) { 0 }
    @JvmStatic
    val HARVEST_TIER = MatProperty("pixellib:harvest_tier".toResLoc(), ::merge) {
        it.harvestTier(1.5f * (it.tier + 1), 1.5f * (it.tier + 1))
    }
    @JvmStatic
    val ITEM_TIER = MatProperty<IItemTier?>("pixellib:item_tier".toResLoc(), ::merge) { null }
    @JvmStatic
    val ARMOR_MATERIAL = MatProperty<IArmorMaterial?>("pixellib:armor_material".toResLoc(), ::merge) { null }
    @JvmStatic
    val ELEMENT = MatProperty("pixellib:element".toResLoc(), ::merge) { BaseElements.NULL }
    @JvmStatic
    val SECOND_NAME = MatProperty("pixellib:second_name".toResLoc(), ::merge, default = Material::name)
    @JvmStatic
    val BURN_TIME = MatProperty("pixellib:burn_time".toResLoc(), ::merge) { 0 }
    @JvmStatic
    val COMPOUND_TYPE = MatProperty("pixellib:compound_type".toResLoc(), ::merge) { CompoundType.CHEMICAL }
    @JvmStatic
    val DENSITY_MULTIPLIER = MatProperty("pixellib:density_multiplier".toResLoc(), ::merge) { 1f }
    @JvmStatic
    val PROCESSING_MULTIPLIER = MatProperty("pixellib:processing_multiplier".toResLoc(), ::merge) { 1 }
    @JvmStatic
    val UNREFINED_COLOR = MatProperty("pixellib:unrefined_color".toResLoc(), ::merge, default = Material::color)
    @JvmStatic
    val FLUID_TEMPERATURE = MatProperty("pixellib:fluid_temperature".toResLoc(), ::merge) { 0 }
    @JvmStatic
    val BOILING_TEMPERATURE = MatProperty("pixellib:boiling_temperature".toResLoc(), ::merge) { 0 }
    @JvmStatic
    val BOILING_MATERIAL = MatProperty("pixellib:boiling_material".toResLoc(), ::merge) { it }
    @JvmStatic
    val REFINED_MATERIAL = MatProperty("pixellib:refined_material".toResLoc(), ::merge) { it }
    @JvmStatic
    val PH = MatProperty("pixellib:ph".toResLoc(), ::merge, { it > 0f && it < 14f }) { 7f }
    @JvmStatic
    val ALPHA = MatProperty("pixellib:alpha".toResLoc(), ::merge) { 0xFF }
    @JvmStatic
    val BLOCK_COMPACTION = MatProperty("pixellib:block_compation".toResLoc(), ::merge) { BlockCompaction.FROM_3X3 }
    @JvmStatic
    val TRANSITION_PROPERTIES = MatProperty<TransitionProperties?>("pixellib:transition_properties".toResLoc(), ::merge) { null }
    @JvmStatic
    val HAS_ORE = MatProperty("pixellib:has_ore".toResLoc(), ::merge) { false }
    @JvmStatic
    val IS_GAS = MatProperty("pixellib:is_gas".toResLoc(), ::merge) { false }
    @JvmStatic
    val SIMPLE_PROCESSING = MatProperty("pixellib:simple_processing".toResLoc(), ::merge) { true }
    @JvmStatic
    val ROD_OUTPUT_COUNT = MatProperty("pixellib:rod_output_count".toResLoc(), ::merge) { 1 }
    @JvmStatic
    val HAS_DUST = MatProperty("pixellib:has_dust".toResLoc(), ::merge) { false }
    @JvmStatic
    val DISPLAY_TYPE = MatProperty("pixellib:display_type".toResLoc(), ::merge) { DisplayType.COMPOUND }
    @JvmStatic
    val MAIN_ITEM_TYPE = MatProperty<ObjectType?>("pixellib:main_item_type".toResLoc(), { first, second ->
        when (first) {
            is ObjectType -> when (second) {
                is ObjectType -> if (second.typePriority > first.typePriority) second else first
                else -> first
            }
            else -> second as? ObjectType
        }
    }, { it?.itemConstructor != null }) { null }
}