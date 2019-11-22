package com.emosewapixel.pixellib.materialsystem.addition

import com.emosewapixel.pixellib.blocks.MaterialBlock
import com.emosewapixel.pixellib.blocks.MaterialBlockItem
import com.emosewapixel.pixellib.fluids.IFluidPairHolder
import com.emosewapixel.pixellib.fluids.MaterialBucketItem
import com.emosewapixel.pixellib.fluids.MaterialFluidBlock
import com.emosewapixel.pixellib.fluids.MaterialFluidHolder
import com.emosewapixel.pixellib.items.MaterialItem
import com.emosewapixel.pixellib.materialsystem.materials.CompoundType
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.materials.TransitionProperties
import com.emosewapixel.pixellib.materialsystem.materials.utility.ct.MaterialRequirement
import com.emosewapixel.pixellib.materialsystem.types.BlockType
import com.emosewapixel.pixellib.materialsystem.types.FluidType
import com.emosewapixel.pixellib.materialsystem.types.ItemType
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import net.minecraft.block.Block
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.BucketItem
import net.minecraft.item.Item

@JvmOverloads
fun dustMaterial(name: String, builder: Material.() -> Unit = { }) = Material(name).apply {
    isDustMaterial = true
    builder()
}.build()

@JvmOverloads
fun gemMaterial(name: String, builder: Material.() -> Unit = { }) = Material(name).apply {
    isGemMaterial = true
    builder()
}.build()

@JvmOverloads
fun ingotMaterial(name: String, builder: Material.() -> Unit = { }) = Material(name).apply {
    isIngotMaterial = true
    compoundType = CompoundType.ALLOY
    builder()
}.build()

@JvmOverloads
fun fluidMaterial(name: String, builder: Material.() -> Unit = { }) = Material(name).apply {
    isFluidMaterial = true
    fluidTemperature = 300
    builder()
}.build()

@JvmOverloads
fun groupMaterial(name: String, builder: Material.() -> Unit = { }) = Material(name).apply {
    isGroupMaterial = true
    builder()
}.build()

@JvmOverloads
fun transitionMaterial(name: String, endMaterial: () -> Material, neededAmount: Int, builder: Material.() -> Unit = { }) = Material(name).apply {
    transitionProperties = TransitionProperties(neededAmount, endMaterial)
    builder()
}.build()

@JvmOverloads
fun itemType(name: String, requirement: (Material) -> Boolean, constructor: (Material, ItemType) -> Item = ::MaterialItem, builder: ItemType.() -> Unit = { }): ObjectType<*, *> {
    val type = ItemType(name, MaterialRequirement { requirement(it) }, constructor)
    type(builder)
    return type.build()
}

fun itemType(name: String, requirement: (Material) -> Boolean, builder: ItemType.() -> Unit): ObjectType<*, *> {
    val type = ItemType(name, MaterialRequirement { requirement(it) })
    type(builder)
    return type.build()
}

@JvmOverloads
fun blockType(name: String, requirement: (Material) -> Boolean, properties: Block.Properties, constructor: (Material, BlockType) -> Block = ::MaterialBlock, itemConstructor: (Material, BlockType) -> BlockItem = ::MaterialBlockItem, builder: BlockType.() -> Unit = { }): ObjectType<*, *> {
    val type = BlockType(name, MaterialRequirement { requirement(it) }, properties, constructor, itemConstructor)
    type(builder)
    return type.build()
}

fun blockType(name: String, requirement: (Material) -> Boolean, properties: Block.Properties, builder: BlockType.() -> Unit): ObjectType<*, *> {
    val type = BlockType(name, MaterialRequirement { requirement(it) }, properties)
    type(builder)
    return type.build()
}

@JvmOverloads
fun fluidType(name: String, requirement: (Material) -> Boolean, fluidConstructor: (Material, FluidType) -> IFluidPairHolder = ::MaterialFluidHolder, blockConstructor: (Material, FluidType) -> FlowingFluidBlock = ::MaterialFluidBlock, bucketConstructor: (Material, FluidType) -> BucketItem = ::MaterialBucketItem, builder: FluidType.() -> Unit = { }): ObjectType<*, *> {
    val type = FluidType(name, MaterialRequirement { requirement(it) }, fluidConstructor, blockConstructor, bucketConstructor)
    type(builder)
    return type.build()
}

fun fluidType(name: String, requirement: (Material) -> Boolean, builder: FluidType.() -> Unit): ObjectType<*, *> {
    val type = FluidType(name, MaterialRequirement { requirement(it) })
    type(builder)
    return type.build()
}