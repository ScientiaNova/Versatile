package com.scientianova.versatile.materialsystem.builders

import com.scientianova.versatile.Versatile
import com.scientianova.versatile.blocks.MaterialBlock
import com.scientianova.versatile.blocks.MaterialBlockItem
import com.scientianova.versatile.common.extensions.json
import com.scientianova.versatile.fluids.MaterialBucketItem
import com.scientianova.versatile.fluids.MaterialFluidBlock
import com.scientianova.versatile.fluids.MaterialFluidHolder
import com.scientianova.versatile.items.MaterialItem
import com.scientianova.versatile.materialsystem.addition.BaseObjTypes
import com.scientianova.versatile.materialsystem.addition.BaseTextureTypes
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.main.ObjectType
import com.scientianova.versatile.materialsystem.properties.CompoundType
import com.scientianova.versatile.materialsystem.properties.DisplayType
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
            "parent" to "versatile:block/materialblocks/" + (if (singleTextureType) "" else "${mat.textureType}/") + name
        }
    }
    builder()
}.register()

@JvmOverloads
fun fluidType(name: String, requirement: (Material) -> Boolean, builder: ObjectType.() -> Unit = { }) = ObjectType(name) { requirement(it) }.apply {
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