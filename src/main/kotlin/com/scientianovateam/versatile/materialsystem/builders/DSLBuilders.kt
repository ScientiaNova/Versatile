package com.scientianovateam.versatile.materialsystem.builders

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.blocks.MaterialBlock
import com.scientianovateam.versatile.blocks.MaterialBlockItem
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.fluids.MaterialBucketItem
import com.scientianovateam.versatile.fluids.MaterialFluidBlock
import com.scientianovateam.versatile.fluids.MaterialFluidHolder
import com.scientianovateam.versatile.items.MaterialItem
import com.scientianovateam.versatile.materialsystem.addition.BaseForms
import com.scientianovateam.versatile.materialsystem.addition.BaseTextureTypes
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.properties.CompoundType
import com.scientianovateam.versatile.materialsystem.properties.DisplayType
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.item.Items

@JvmOverloads
fun material(vararg names: String, builder: Material.() -> Unit = { }) = Material(*names).apply { builder() }.register()

@JvmOverloads
fun dustMaterial(vararg names: String, builder: Material.() -> Unit = { }) = Material(*names).apply {
    mainItemType = BaseForms.DUST
    hasDust = true
    builder()
}.register()

@JvmOverloads
fun gemMaterial(vararg names: String, builder: Material.() -> Unit = { }) = Material(*names).apply {
    mainItemType = BaseForms.GEM
    hasDust = true
    builder()
}.register()

@JvmOverloads
fun ingotMaterial(vararg names: String, builder: Material.() -> Unit = { }) = Material(*names).apply {
    mainItemType = BaseForms.INGOT
    compoundType = CompoundType.ALLOY
    hasDust = true
    builder()
}.register()

@JvmOverloads
fun fluidMaterial(vararg names: String, builder: Material.() -> Unit = { }) = Material(*names).apply {
    textureType = BaseTextureTypes.FLUID
    fluidTemperature = 300
    builder()
}.register()

@JvmOverloads
fun groupMaterial(vararg names: String, builder: Material.() -> Unit = { }) = Material(*names).apply {
    displayType = DisplayType.GROUP
    builder()
}.register()

@JvmOverloads
fun form(name: String, requirement: (Material) -> Boolean, builder: Form.() -> Unit = { }) = Form(name) { requirement(it) }.apply {
    builder()
}.register()

@JvmOverloads
fun itemForm(name: String, requirement: (Material) -> Boolean, builder: Form.() -> Unit = { }) = Form(name) { requirement(it) }.apply {
    itemConstructor = { mat -> MaterialItem(mat, this) }
    builder()
}.register()

@JvmOverloads
fun blockForm(name: String, requirement: (Material) -> Boolean, builder: Form.() -> Unit = { }) = Form(name) { requirement(it) }.apply {
    itemConstructor = { mat -> MaterialBlockItem(mat, this) }
    blockConstructor = { mat -> MaterialBlock(mat, this) }
    itemModel = { mat ->
        json {
            "parent" to "versatile:block/materialblocks/" + (if (singleTextureType) "" else "${mat.textureType}/") + name
        }
    }
    builder()
}.register()

@JvmOverloads
fun fluidForm(name: String, requirement: (Material) -> Boolean, builder: Form.() -> Unit = { }) = Form(name) { requirement(it) }.apply {
    itemConstructor = { mat -> MaterialBucketItem(mat, this) }
    blockConstructor = { mat -> MaterialFluidBlock(mat, this) }
    fluidPairConstructor = { mat -> MaterialFluidHolder(mat, this) }
    itemProperties = { Item.Properties().group(Versatile.MAIN).containerItem(Items.BUCKET).maxStackSize(1) }
    blockProperties = { Block.Properties.from(Blocks.WATER) }
    singleTextureType = true
    indexBlackList += 0
    bucketVolume = 1000
    itemTagName = "forge:buckets/$name"
    blockTagName = ""
    itemModel = { mat ->
        json {
            "parent" to "versatile:item/materialitems/" + (if (singleTextureType) "" else "${mat.textureType}/") + name + (if (isGas(mat)) "_gas" else "") + "_bucket"
        }
    }
    builder()
}.register()