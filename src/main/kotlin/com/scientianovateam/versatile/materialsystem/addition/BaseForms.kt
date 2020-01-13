package com.scientianovateam.versatile.materialsystem.addition

import com.scientianovateam.versatile.common.extensions.json
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
import net.minecraftforge.common.ToolType
import net.minecraft.block.material.Material as BlockMaterial

object BaseForms {
    @JvmField
    val DUST = itemForm("dust", Material::hasDust) {
        bucketVolume = 144
        typePriority = 1
    }
    @JvmField
    val GEM = itemForm("gem", Material::hasGem) {
        bucketVolume = 144
        typePriority = 2
    }
    @JvmField
    val INGOT = itemForm("ingot", Material::hasIngot) {
        bucketVolume = 144
        typePriority = 2
    }
    @JvmField
    val NUGGET = itemForm("nugget", Material::hasIngot) {
        bucketVolume = 16
    }
    @JvmField
    val BLOCK = blockForm("storage_block", { it.blockCompaction != BlockCompaction.NONE }) {
        registryName = { ResourceLocation("versatile:${it.name}_block") }
        burnTime = { it.burnTime * 10 }
        bucketVolume = 1296
    }
    @JvmField
    val ORE = blockForm("ore", Material::hasOre) {
        blockProperties = { Block.Properties.create(BlockMaterial.ROCK).sound(SoundType.STONE).hardnessAndResistance(it.hardness, it.resistance).harvestLevel(it.harvestTier).harvestTool(ToolType.PICKAXE) }
        color = Material::unrefinedColor
        indexBlackList += 1
        bucketVolume = 144
        burnTime = { 0 }
    }
    @JvmField
    val FLUID = fluidForm("liquid", { it.liquidTemperature > 0 }) {
        registryName = { ResourceLocation("versatile:${it.liquidName}") }
        fluidTagName = "forge:"
        itemTagName = "forge:buckets"
        temperature = Material::liquidTemperature
        fluidAttributes = { mat ->
            MaterialFluidAttributes.Builder(mat, this, "minecraft:block/water".toResLoc()).apply {
                overlay("minecraft:block/water_overlay".toResLoc())
            }
        }
    }
    @JvmField
    val GAS = fluidForm("gas", { it.gasTemperature > 0 }) {
        registryName = { ResourceLocation("versatile:${it.gasTemperature}") }
        fluidTagName = "forge:"
        itemTagName = "forge:buckets"
        temperature = Material::gasTemperature
        fluidAttributes = { mat ->
            MaterialFluidAttributes.Builder(mat, this, "minecraft:block/water".toResLoc()).apply {
                overlay("minecraft:block/water_overlay".toResLoc())
                gaseous()
            }
        }
        itemModel = { mat ->
            json {
                "parent" to "versatile:item/materialitems/${if (singleTextureType) "" else "${mat.textureSet}/"}${name}_gas_bucket"
            }
        }
    }
}