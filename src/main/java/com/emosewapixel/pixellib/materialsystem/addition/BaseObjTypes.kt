package com.emosewapixel.pixellib.materialsystem.addition

import com.emosewapixel.pixellib.extensions.toResLoc
import com.emosewapixel.pixellib.fluids.MaterialFluidAttributes
import com.emosewapixel.pixellib.materialsystem.main.Material
import com.emosewapixel.pixellib.materialsystem.properties.BlockCompaction
import com.emosewapixel.pixellib.materialsystem.properties.ObjTypeProperties
import com.emosewapixel.pixellib.materialsystem.properties.fromTier
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvents
import net.minecraftforge.common.ToolType
import net.minecraft.block.material.Material as BlockMaterial

object BaseObjTypes {
    @JvmField
    val DUST = itemType("dust", Material::hasDust) {
        bucketVolume = 144
    }
    @JvmField
    val GEM = itemType("gem", Material::isGemMaterial) {
        bucketVolume = 144
    }
    @JvmField
    val INGOT = itemType("ingot", Material::isIngotMaterial) {
        bucketVolume = 144
    }
    @JvmField
    val NUGGET = itemType("nugget", Material::isIngotMaterial) {
        bucketVolume = 16
    }
    @JvmField
    val BLOCK = blockType("storage_block", { it.isItemMaterial && it.blockCompaction != BlockCompaction.NONE }) {
        buildRegistryName = { ResourceLocation("pixellib:${it.name}_block") }
        burnTime = { it.standardBurnTime * 10 }
        bucketVolume = 1296
    }
    @JvmField
    val ORE = blockType("ore", Material::hasOre) {
        blockProperties = { Block.Properties.create(BlockMaterial.ROCK).sound(SoundType.STONE).fromTier(it.harvestTier).harvestTool(ToolType.PICKAXE) }
        color = Material::unrefinedColor
        indexBlackList += 1
        bucketVolume = 144
        burnTime = { 0 }
    }
    @JvmField
    val FLUID = fluidType("fluid", Material::isFluidMaterial) {
        buildRegistryName = { ResourceLocation("pixellib:${it.name}") }
        fluidTagName = "forge:"
        fluidAttributes = { mat ->
            MaterialFluidAttributes.Builder(mat, this, "minecraft:block/water".toResLoc()).apply {
                overlay("minecraft:block/water_overlay".toResLoc())
                if (isGas(mat)) gaseous()
            }
        }
    }
    @JvmField
    val MOLTEN_FLUID = fluidType("molten", { it.isItemMaterial && it.fluidTemperature > 0 }) {
        buildRegistryName = { ResourceLocation("pixellib:molten_${it.name}") }
        fluidAttributes = { mat ->
            ObjTypeProperties.FLUID_ATTRIBUTES.default(this)(mat).sound(SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundEvents.ITEM_BUCKET_EMPTY_LAVA)
        }
    }
}