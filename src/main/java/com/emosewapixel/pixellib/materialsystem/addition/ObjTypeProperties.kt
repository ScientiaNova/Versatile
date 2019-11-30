package com.emosewapixel.pixellib.materialsystem.addition

import com.emosewapixel.pixellib.PixelLib
import com.emosewapixel.pixellib.extensions.json
import com.emosewapixel.pixellib.extensions.toResLoc
import com.emosewapixel.pixellib.fluids.IFluidPairHolder
import com.emosewapixel.pixellib.fluids.MaterialFluidAttributes
import com.emosewapixel.pixellib.materialsystem.main.Material
import com.emosewapixel.pixellib.materialsystem.properties.HarvestTier
import com.emosewapixel.pixellib.materialsystem.properties.ObjTypeProperty
import com.emosewapixel.pixellib.materialsystem.properties.merge
import com.google.gson.JsonObject
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.ToolType
import net.minecraftforge.fluids.FluidAttributes
import net.minecraft.block.material.Material as BlockMaterial

object ObjTypeProperties {
    @JvmStatic
    val BUCKET_VOLUME = ObjTypeProperty("pixellib:bucket_volume".toResLoc(), ::merge) { 0 }
    @JvmStatic
    val REGISTRY_NAME_FUM = ObjTypeProperty<(Material) -> ResourceLocation>("pixellib:registry_name_fun".toResLoc(), ::merge) { type ->
        { mat -> "pixellib:${mat.name}_${type.name}".toResLoc() }
    }
    @JvmStatic
    val ITEM_TAG = ObjTypeProperty("pixellib:item_tag".toResLoc(), ::merge) { type -> "forge:${type}s" }
    @JvmStatic
    val BLOCK_TAG = ObjTypeProperty("pixellib:block_tag".toResLoc(), ::merge) { type -> "forge:${type}s" }
    @JvmStatic
    val FLUID_TAG = ObjTypeProperty("pixellib:fluid_tag".toResLoc(), ::merge) { type -> "forge:${type}_" }
    @JvmStatic
    val COLOR_FUN = ObjTypeProperty<(Material) -> Int>("pixellib:color_fun".toResLoc(), ::merge) { Material::color }
    @JvmStatic
    val DENSITY_MULTIPLIER = ObjTypeProperty("pixellib:density_multiplier".toResLoc(), ::merge) { 1f }
    @JvmStatic
    val IS_GAS = ObjTypeProperty<(Material) -> Boolean>("pixellib:is_gas".toResLoc(), ::merge) { Material::isGas }
    @JvmStatic
    val TEMPERATURE = ObjTypeProperty<(Material) -> Int>("pixellib:temperature".toResLoc(), ::merge) { { mat -> mat.fluidTemperature.let { if (it > 0) it else 300 } } }
    @JvmStatic
    val SINGLE_TEXTURE_TYPE = ObjTypeProperty("pixellib:single_texture_type".toResLoc(), ::merge) { false }
    @JvmStatic
    val BURN_TIME = ObjTypeProperty<(Material) -> Int>("pixellib:burn_time".toResLoc(), ::merge) { type -> { mat -> (mat.standardBurnTime * (type.bucketVolume / 144f)).toInt() } }
    @JvmStatic
    val ITEM_MODEL = ObjTypeProperty<(Material) -> JsonObject>("pixellib:item_model".toResLoc(), ::merge) { type ->
        { mat ->
            json {
                "parent" to "pixellib:item/materialitems/" + (if (type.singleTextureType) "" else "${mat.textureType}/") + type.name
            }
        }
    }
    @JvmStatic
    val BLOCKSTATE_JSON = ObjTypeProperty<(Material) -> JsonObject>("pixellib:blockstate_json".toResLoc(), ::merge) { type ->
        { mat ->
            json {
                "variants" {
                    "" {
                        "model" to "pixellib:block/materialblocks/" + (if (type.singleTextureType) "" else "${mat.textureType}/") + type.name
                    }
                }
            }
        }
    }
    @JvmStatic
    val ITEM_CONSTRUCTOR = ObjTypeProperty<((Material) -> Item)?>("pixellib:item_constructor".toResLoc(), ::merge) { null }
    @JvmStatic
    val ITEM_PROPERTIES = ObjTypeProperty<(Material) -> Item.Properties>("pixellib:item_properties".toResLoc(), ::merge) { { Item.Properties().group(PixelLib.MAIN) } }
    @JvmStatic
    val BLOCK_CONSTRUCTOR = ObjTypeProperty<((Material) -> Block)?>("pixellib:block_constructor".toResLoc(), ::merge) { null }
    @JvmStatic
    val BLOCK_PROPERTIES = ObjTypeProperty<(Material) -> Block.Properties>("pixellib:block_properties".toResLoc(), ::merge) {
        { mat -> Block.Properties.create(BlockMaterial.IRON).sound(SoundType.METAL).fromTier(mat.harvestTier).harvestTool(ToolType.PICKAXE) }
    }
    @JvmStatic
    val FLUID_CONSTRUCTOR = ObjTypeProperty<((Material) -> IFluidPairHolder)?>("pixellib:fluid_constructor".toResLoc(), ::merge) { null }
    @JvmStatic
    val FLUID_ATTRIBUTES = ObjTypeProperty<(Material) -> FluidAttributes.Builder>("pixellib:fluid_attributes".toResLoc(), ::merge) { type ->
        { mat ->
            MaterialFluidAttributes.Builder(mat, type, "pixellib:fluid/${type.name}".toResLoc()).apply { if (type.isGas(mat)) gaseous() }
        }
    }
    @JvmStatic
    val TYPE_PRIORITY = ObjTypeProperty("pixellib:type_priority".toResLoc(), ::merge) { 0 }
}

fun Block.Properties.fromTier(tier: HarvestTier): Block.Properties = this.hardnessAndResistance(tier.hardness, tier.resistance).harvestLevel(tier.harvestLevel)