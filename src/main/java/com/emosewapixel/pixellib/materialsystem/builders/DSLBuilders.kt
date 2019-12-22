package com.emosewapixel.pixellib.materialsystem.builders

import com.emosewapixel.pixellib.PixelLib
import com.emosewapixel.pixellib.blocks.MaterialBlock
import com.emosewapixel.pixellib.blocks.MaterialBlockItem
import com.emosewapixel.pixellib.extensions.json
import com.emosewapixel.pixellib.fluids.MaterialBucketItem
import com.emosewapixel.pixellib.fluids.MaterialFluidBlock
import com.emosewapixel.pixellib.fluids.MaterialFluidHolder
import com.emosewapixel.pixellib.items.MaterialItem
import com.emosewapixel.pixellib.materialsystem.addition.BaseObjTypes
import com.emosewapixel.pixellib.materialsystem.addition.BaseTextureTypes
import com.emosewapixel.pixellib.materialsystem.main.Material
import com.emosewapixel.pixellib.materialsystem.main.ObjectType
import com.emosewapixel.pixellib.materialsystem.properties.CompoundType
import com.emosewapixel.pixellib.materialsystem.properties.DisplayType
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.item.Items

@JvmOverloads
fun material(vararg names: String, builder: Material.() -> Unit = { }) = Material(*names).apply { builder() }.register()

@JvmOverloads
fun dustMaterial(vararg names: String, builder: Material.() -> Unit = { }) = Material(*names).apply {
    mainItemType = BaseObjTypes.DUST
    hasDust = true
    builder()
}.register()

@JvmOverloads
fun gemMaterial(vararg names: String, builder: Material.() -> Unit = { }) = Material(*names).apply {
    mainItemType = BaseObjTypes.GEM
    hasDust = true
    builder()
}.register()

@JvmOverloads
fun ingotMaterial(vararg names: String, builder: Material.() -> Unit = { }) = Material(*names).apply {
    mainItemType = BaseObjTypes.INGOT
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
fun objType(name: String, requirement: (Material) -> Boolean, builder: ObjectType.() -> Unit = { }) = ObjectType(name) { requirement(it) }.apply {
    builder()
}.register()

@JvmOverloads
fun itemType(name: String, requirement: (Material) -> Boolean, builder: ObjectType.() -> Unit = { }) = ObjectType(name) { requirement(it) }.apply {
    itemConstructor = { mat -> MaterialItem(mat, this) }
    builder()
}.register()

@JvmOverloads
fun blockType(name: String, requirement: (Material) -> Boolean, builder: ObjectType.() -> Unit = { }) = ObjectType(name) { requirement(it) }.apply {
    itemConstructor = { mat -> MaterialBlockItem(mat, this) }
    blockConstructor = { mat -> MaterialBlock(mat, this) }
    itemModel = { mat ->
        json {
            "parent" to "pixellib:block/materialblocks/" + (if (singleTextureType) "" else "${mat.textureType}/") + name
        }
    }
    builder()
}.register()

@JvmOverloads
fun fluidType(name: String, requirement: (Material) -> Boolean, builder: ObjectType.() -> Unit = { }) = ObjectType(name) { requirement(it) }.apply {
    itemConstructor = { mat -> MaterialBucketItem(mat, this) }
    blockConstructor = { mat -> MaterialFluidBlock(mat, this) }
    fluidPairConstructor = { mat -> MaterialFluidHolder(mat, this) }
    itemProperties = { Item.Properties().group(PixelLib.MAIN).containerItem(Items.BUCKET).maxStackSize(1) }
    blockProperties = { Block.Properties.from(Blocks.WATER) }
    singleTextureType = true
    indexBlackList += 0
    bucketVolume = 1000
    itemTagName = "forge:buckets/$name"
    blockTagName = ""
    itemModel = { mat ->
        json {
            "parent" to "pixellib:item/materialitems/" + (if (singleTextureType) "" else "${mat.textureType}/") + name + (if (isGas(mat)) "_gas" else "") + "_bucket"
        }
    }
    builder()
}.register()