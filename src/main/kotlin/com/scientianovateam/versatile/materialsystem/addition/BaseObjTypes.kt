package com.scientianovateam.versatile.materialsystem.addition

import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.fluids.MaterialFluidAttributes
import com.scientianovateam.versatile.materialsystem.builders.blockForm
import com.scientianovateam.versatile.materialsystem.builders.fluidForm
import com.scientianovateam.versatile.materialsystem.builders.itemForm
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.properties.BlockCompaction
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvents
import net.minecraftforge.common.ToolType
import net.minecraft.block.material.Material as BlockMaterial

object BaseForms {
    @JvmField
    val DUST = itemForm("dust", Material::hasDust) {
        bucketVolume = 144
        typePriority = 1
    }
    @JvmField
    val GEM = itemForm("gem", Material::isGemMaterial) {
        bucketVolume = 144
        typePriority = 2
    }
    @JvmField
    val INGOT = itemForm("ingot", Material::isIngotMaterial) {
        bucketVolume = 144
        typePriority = 2
    }
    @JvmField
    val NUGGET = itemForm("nugget", Material::isIngotMaterial) {
        bucketVolume = 16
    }
    @JvmField
    val BLOCK = blockForm("storage_block", { it.isItemMaterial && it.blockCompaction != BlockCompaction.NONE }) {
        registryName = { ResourceLocation("versatile:${it.name}_block") }
        burnTime = { it.standardBurnTime * 10 }
        bucketVolume = 1296
    }
    @JvmField
    val ORE = blockForm("ore", Material::hasOre) {
        blockProperties = { Block.Properties.create(BlockMaterial.ROCK).sound(SoundType.STONE).fromTier(it.harvestTier).harvestTool(ToolType.PICKAXE) }
        color = Material::unrefinedColor
        indexBlackList += 1
        bucketVolume = 144
        burnTime = { 0 }
    }
    @JvmField
    val FLUID = fluidForm("fluid", Material::isFluidMaterial) {
        registryName = { ResourceLocation("versatile:${it.name}") }
        fluidTagName = "forge:"
        itemTagName = "forge:buckets"
        fluidAttributes = { mat ->
            MaterialFluidAttributes.Builder(mat, this, "minecraft:block/water".toResLoc()).apply {
                overlay("minecraft:block/water_overlay".toResLoc())
                if (isGas(mat)) gaseous()
            }
        }
    }
    @JvmField
    val MOLTEN_FLUID = fluidForm("molten", { it.isItemMaterial && it.fluidTemperature > 0 }) {
        registryName = { ResourceLocation("versatile:molten_${it.name}") }
        fluidAttributes = { mat ->
            LegacyFormProperties.FLUID_ATTRIBUTES.default(this)(mat).sound(SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundEvents.ITEM_BUCKET_EMPTY_LAVA)
        }
    }
}