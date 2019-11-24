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
import com.emosewapixel.pixellib.materialsystem.main.ct.MaterialRequirement
import com.emosewapixel.pixellib.materialsystem.properties.CompoundType
import com.emosewapixel.pixellib.materialsystem.properties.DisplayType
import com.emosewapixel.pixellib.materialsystem.properties.TransitionProperties
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.item.Items

@JvmOverloads
fun material(name: String, builder: Material.() -> Unit = { }) = Material(name).apply { builder() }.register()

@JvmOverloads
fun dustMaterial(name: String, builder: Material.() -> Unit = { }) = Material(name).apply {
    mainItemType = BaseObjTypes.DUST
    hasDust = true
    builder()
}.register()

@JvmOverloads
fun gemMaterial(name: String, builder: Material.() -> Unit = { }) = Material(name).apply {
    mainItemType = BaseObjTypes.GEM
    hasDust = true
    builder()
}.register()

@JvmOverloads
fun ingotMaterial(name: String, builder: Material.() -> Unit = { }) = Material(name).apply {
    mainItemType = BaseObjTypes.INGOT
    compoundType = CompoundType.ALLOY
    hasDust = true
    builder()
}.register()

@JvmOverloads
fun fluidMaterial(name: String, builder: Material.() -> Unit = { }) = Material(name).apply {
    textureType = BaseTextureTypes.FLUID
    fluidTemperature = 300
    builder()
}.register()

@JvmOverloads
fun groupMaterial(name: String, builder: Material.() -> Unit = { }) = Material(name).apply {
    displayType = DisplayType.GROUP
    builder()
}.register()

@JvmOverloads
fun transitionMaterial(name: String, neededAmount: Int, endMaterial: () -> Material, builder: Material.() -> Unit = { }) = Material(name).apply {
    transitionProperties = TransitionProperties(neededAmount, endMaterial)
    builder()
}.register()

@JvmOverloads
fun objType(name: String, requirement: (Material) -> Boolean, builder: ObjectType.() -> Unit = { }) = ObjectType(name, MaterialRequirement { requirement(it) }).apply {
    builder()
}.build()

@JvmOverloads
fun itemType(name: String, requirement: (Material) -> Boolean, builder: ObjectType.() -> Unit = { }) = ObjectType(name, MaterialRequirement { requirement(it) }).apply {
    itemConstructor = { mat -> MaterialItem(mat, this) }
    builder()
}.build()

@JvmOverloads
fun blockType(name: String, requirement: (Material) -> Boolean, builder: ObjectType.() -> Unit = { }) = ObjectType(name, MaterialRequirement { requirement(it) }).apply {
    itemConstructor = { mat -> MaterialBlockItem(mat, this) }
    blockConstructor = { mat -> MaterialBlock(mat, this) }
    itemModel = { mat ->
        json {
            "parent" to "pixellib:block/materialblocks/" + (if (singleTextureType) "" else "${mat.textureType}/") + name
        }
    }
    builder()
}.build()

@JvmOverloads
fun fluidType(name: String, requirement: (Material) -> Boolean, builder: ObjectType.() -> Unit = { }) = ObjectType(name, MaterialRequirement { requirement(it) }).apply {
    itemConstructor = { mat -> MaterialBucketItem(mat, this) }
    blockConstructor = { mat -> MaterialFluidBlock(mat, this) }
    fluidPairConstructor = { mat -> MaterialFluidHolder(mat, this) }
    itemProperties = { Item.Properties().group(PixelLib.MAIN).containerItem(Items.BUCKET).maxStackSize(1) }
    blockProperties = { Block.Properties.from(Blocks.WATER) }
    singleTextureType = true
    indexBlackList += 0
    bucketVolume = 1000
    itemTagName = ""
    blockTagName = ""
    itemModel = { mat ->
        json {
            "parent" to "pixellib:item/materialitems/" + (if (singleTextureType) "" else "${mat.textureType}/") + name + (if (isGas(mat)) "_gas" else "") + "_bucket"
        }
    }
    builder()
}.build()