package com.scientianova.versatile.materialsystem.builders

import com.scientianova.versatile.Versatile
import com.scientianova.versatile.blocks.ExtendedBlockProperties
import com.scientianova.versatile.blocks.VersatileBlock
import com.scientianova.versatile.blocks.VersatileBlockItem
import com.scientianova.versatile.common.extensions.json
import com.scientianova.versatile.fluids.MaterialBucketItem
import com.scientianova.versatile.fluids.MaterialFluidBlock
import com.scientianova.versatile.fluids.MaterialFluidHolder
import com.scientianova.versatile.items.ExtendedItemProperties
import com.scientianova.versatile.items.VersatileItem
import com.scientianova.versatile.materialsystem.addition.*
import com.scientianova.versatile.materialsystem.main.GlobalForm
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.properties.CompoundType
import com.scientianova.versatile.materialsystem.properties.DisplayType
import net.minecraft.util.text.LanguageMap
import net.minecraft.util.text.TranslationTextComponent
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
    liquidNames = listOf("molten_$name")
    compoundType = CompoundType.ALLOY
    hasDust = true
    hasIngot = true
    builder()
}.register()

fun fluidMaterial(vararg names: String, builder: Material.() -> Unit) = Material().apply {
    associatedNames = listOf(*names)
    textureSet = BaseTextureSets.FLUID
    liquidTemperature = 300
    builder()
}.register()

fun groupMaterial(vararg names: String, builder: Material.() -> Unit) = Material().apply {
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
                localizedNameFun = {
                    if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey)
                    else localize()
                }
        ))
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
    ITEM { MaterialBucketItem(mat, this) }
    BLOCK { MaterialFluidBlock(mat, this) }
    FLUID { MaterialFluidHolder(mat, this) }
    COMBINED_ITEM_TAGS { emptyList() }
    COMBINED_BLOCK_TAGS { emptyList() }
    builder()
}.register()