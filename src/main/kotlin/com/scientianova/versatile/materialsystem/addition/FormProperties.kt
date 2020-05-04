@file:JvmName("FormProperties")

package com.scientianova.versatile.materialsystem.addition

import com.scientianova.versatile.common.extensions.json
import com.scientianova.versatile.common.extensions.toResLoc
import com.scientianova.versatile.fluids.IFluidPairHolder
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.properties.FormProperty
import com.scientianova.versatile.materialsystem.properties.GlobalFormProperty
import com.scientianova.versatile.materialsystem.properties.HarvestTier
import net.minecraft.block.Block
import net.minecraft.item.Item

val NAME = GlobalFormProperty("versatile:name".toResLoc(), String::isNotBlank) { "" }
val GENERATE = GlobalFormProperty<(Material) -> Boolean>("versatile:generate".toResLoc()) { { false } }
val INDEX_BLACKLIST = GlobalFormProperty("versatile:index_blacklist".toResLoc()) { emptyList<Int>() }
val BUCKET_VOLUME = GlobalFormProperty("versatile:bucket_volume".toResLoc()) { 0 }
val REGISTRY_NAME = FormProperty("versatile:registry_name".toResLoc()) {
    "versatile:${mat.name}_${global.name}".toResLoc()
}
val FORM_COLOR = FormProperty("versatile:color".toResLoc()) { mat.color }
val FORM_DENSITY_MULTIPLIER = FormProperty("versatile:density_multiplier".toResLoc()) { 1f }
val TEMPERATURE = FormProperty("versatile:temperature".toResLoc()) { 300 }
val SINGLE_TEXTURE_SET = GlobalFormProperty("versatile:single_texture_set".toResLoc()) { false }
val BURN_TIME = FormProperty("versatile:burn_time".toResLoc()) {
    (mat.standardBurnTime * (global.bucketVolume / 144f)).toInt()
}
val ITEM_TAG = GlobalFormProperty("versatile:item_tag".toResLoc()) { "forge:${name}s" }
val BLOCK_TAG = GlobalFormProperty("versatile:block_tag".toResLoc()) { "forge:${name}s" }
val COMBINED_ITEM_TAGS = FormProperty("versatile:combined_item_tags".toResLoc()) {
    mat.associatedNames.map { "${global.itemTagName}/$it" }
}
val COMBINED_BLOCK_TAGS = FormProperty("versatile:combined_block_tags".toResLoc()) {
    mat.associatedNames.map { "${global.blockTagName}/$it" }
}
val COMBINED_FLUID_TAGS = FormProperty("versatile:combined_fluid_tags".toResLoc()) {
    mat.associatedNames.map { "forge:${global}_$it" }
}
val ITEM_MODEL = FormProperty("versatile:item_model".toResLoc()) {
    json {
        "parent" to "versatile:item/materialitems/" + (if (global.singleTextureSet) "" else "${mat.textureSet}/") + global.name
    }
}
val BLOCKSTATE_JSON = FormProperty("versatile:blockstate_json".toResLoc()) {
    json {
        "variants" {
            "" {
                "model" to "versatile:block/materialblocks/" + (if (global.singleTextureSet) "" else "${mat.textureSet}/") + global.name
            }
        }
    }
}
val ITEM = FormProperty<Item?>("versatile:item".toResLoc()) { null }
val BLOCK = FormProperty<Block?>("versatile:block".toResLoc()) { null }
val FLUID = FormProperty<IFluidPairHolder?>("versatile:fluid".toResLoc()) { null }

fun Block.Properties.fromTier(tier: HarvestTier): Block.Properties = this.hardnessAndResistance(tier.hardness, tier.resistance).harvestLevel(tier.harvestLevel)