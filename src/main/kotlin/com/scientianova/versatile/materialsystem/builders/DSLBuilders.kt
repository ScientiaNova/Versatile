package com.scientianova.versatile.materialsystem.builders

import com.scientianova.versatile.Versatile
import com.scientianova.versatile.blocks.ExtendedBlockProperties
import com.scientianova.versatile.blocks.VersatileBlock
import com.scientianova.versatile.blocks.VersatileBlockItem
import com.scientianova.versatile.common.extensions.json
import com.scientianova.versatile.fluids.VersatileBucketItem
import com.scientianova.versatile.fluids.VersatileFluidBlock
import com.scientianova.versatile.items.ExtendedItemProperties
import com.scientianova.versatile.items.VersatileItem
import com.scientianova.versatile.materialsystem.addition.*
import com.scientianova.versatile.materialsystem.main.GlobalForm
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.properties.BlockCompaction
import com.scientianova.versatile.materialsystem.properties.CompoundType
import com.scientianova.versatile.materialsystem.properties.DisplayType
import net.minecraft.client.renderer.RenderType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.LanguageMap
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.ForgeFlowingFluid
import net.minecraftforge.fml.DistExecutor
import java.util.function.Supplier
import net.minecraft.block.material.Material as BlockMaterial

fun material(vararg names: String, builder: Material.() -> Unit) = Material().apply {
    associatedNames = listOf(*names)
    builder()
}.register()

fun dustMaterial(vararg names: String, builder: Material.() -> Unit) = Material().apply {
    associatedNames = listOf(*names)
    hasDust = true
    builder()
}.register()

fun gemMaterial(vararg names: String, builder: Material.() -> Unit) = Material().apply {
    associatedNames = listOf(*names)
    hasDust = true
    hasGem = true
    builder()
}.register()

fun ingotMaterial(vararg names: String, builder: Material.() -> Unit) = Material().apply {
    associatedNames = listOf(*names)
    liquidNames = names.map { "molten_$it" }
    gasNames = names.map { "${it}_gas" }
    compoundType = CompoundType.ALLOY
    hasDust = true
    hasIngot = true
    builder()
}.register()

fun liquidMaterial(vararg names: String, builder: Material.() -> Unit) = Material().apply {
    blockCompaction = BlockCompaction.NONE
    associatedNames = listOf(*names)
    textureSet = FLUID
    liquidTemperature = 300
    builder()
}.register()

fun gasMaterial(vararg names: String, builder: Material.() -> Unit) = Material().apply {
    blockCompaction = BlockCompaction.NONE
    associatedNames = listOf(*names)
    textureSet = FLUID
    gasTemperature = 300
    builder()
}.register()

fun groupMaterial(vararg names: String, builder: Material.() -> Unit) = Material().apply {
    associatedNames = listOf(*names)
    displayType = DisplayType.GROUP
    builder()
}.register()

fun form(name: String, builder: GlobalForm.() -> Unit) = GlobalForm().apply {
    this.name = name
    builder()
}.register()

fun itemForm(name: String, builder: GlobalForm.() -> Unit) = GlobalForm().apply {
    this.name = name
    ITEM {
        VersatileItem(ExtendedItemProperties(
                group = Versatile.MAIN,
                burnTime = burnTime,
                localizedNameFun = {
                    if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey)
                    else localize()
                }
        )).setRegistryName(registryName)
    }
    builder()
}.register()

fun blockForm(name: String, builder: GlobalForm.() -> Unit) = GlobalForm().apply {
    this.name = name
    ITEM {
        VersatileBlockItem(block!!, ExtendedItemProperties(
                group = Versatile.MAIN,
                burnTime = burnTime
        )).setRegistryName(registryName)
    }
    BLOCK {
        VersatileBlock(ExtendedBlockProperties(
                material = BlockMaterial.IRON,
                hardness = mat.harvestTier.hardness,
                resistance = mat.harvestTier.resistance,
                localizedNameFun = {
                    if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey)
                    else localize()
                }
        )).setRegistryName(registryName)
    }
    ITEM_MODEL {
        json {
            "parent" to "versatile:block/materialblocks/" + (if (singleTextureSet) "" else "${mat.textureSet}/") + name
        }
    }
    builder()
}.register()

fun fluidForm(name: String, builder: GlobalForm.() -> Unit) = GlobalForm().apply {
    this.name = name
    singleTextureSet = true
    indexBlacklist = listOf(0)
    bucketVolume = 1000
    ITEM {
        VersatileBucketItem({ stillFluid!! }, ExtendedItemProperties(
                group = Versatile.MAIN,
                maxStackSize = 1,
                burnTime = burnTime
        )).setRegistryName(registryName)
    }
    BLOCK {
        VersatileFluidBlock({ stillFluid!! }, ExtendedBlockProperties(
                material = BlockMaterial.WATER,
                blocksMovement = false
        )).setRegistryName(registryName)
    }
    STILL_FLUID {
        ForgeFlowingFluid.Source(fluidProperties!!).apply {
            registryName = this@STILL_FLUID.registryName
        }
    }
    FLOWING_FLUID {
        ForgeFlowingFluid.Flowing(fluidProperties!!).apply {
            val stillRegName = this@FLOWING_FLUID.registryName
            registryName = ResourceLocation(stillRegName.namespace, "flowing_${stillRegName.path}")
        }
    }
    RENDER_TYPE { DistExecutor.runForDist<RenderType?>({ Supplier { RenderType.getTranslucent() } }, { Supplier { null } }) }
    COMBINED_ITEM_TAGS { emptyList() }
    COMBINED_BLOCK_TAGS { emptyList() }
    builder()
}.register()