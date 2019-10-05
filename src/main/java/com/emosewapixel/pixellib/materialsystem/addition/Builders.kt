package com.emosewapixel.pixellib.materialsystem.addition

import com.emosewapixel.pixellib.blocks.MaterialBlock
import com.emosewapixel.pixellib.blocks.MaterialBlockItem
import com.emosewapixel.pixellib.fluids.IFluidPairHolder
import com.emosewapixel.pixellib.fluids.MaterialBucketItem
import com.emosewapixel.pixellib.fluids.MaterialFluidBlock
import com.emosewapixel.pixellib.fluids.MaterialFluidHolder
import com.emosewapixel.pixellib.items.MaterialItem
import com.emosewapixel.pixellib.materialsystem.materials.*
import com.emosewapixel.pixellib.materialsystem.materials.utility.GroupMaterial
import com.emosewapixel.pixellib.materialsystem.materials.utility.TransitionMaterial
import com.emosewapixel.pixellib.materialsystem.types.BlockType
import com.emosewapixel.pixellib.materialsystem.types.FluidType
import com.emosewapixel.pixellib.materialsystem.types.ItemType
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import net.minecraft.block.Block
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.item.BucketItem
import net.minecraft.item.Item

@JvmOverloads
fun dustMaterial(name: String, textureType: String, color: Int, tier: Int, builder: DustMaterial.() -> Unit = { }): Material {
    val mat = DustMaterial(name, textureType, color, tier)
    mat(builder)
    return mat.build()
}

@JvmOverloads
fun gemMaterial(name: String, textureType: String, color: Int, tier: Int, builder: GemMaterial.() -> Unit = { }): Material {
    val mat = GemMaterial(name, textureType, color, tier)
    mat(builder)
    return mat.build()
}

@JvmOverloads
fun ingotMaterial(name: String, textureType: String, color: Int, tier: Int, builder: IngotMaterial.() -> Unit = { }): Material {
    val mat = IngotMaterial(name, textureType, color, tier)
    mat(builder)
    return mat.build()
}

@JvmOverloads
fun fluidMaterial(name: String, textureType: String, color: Int, tier: Int = 0, builder: FluidMaterial.() -> Unit = { }): Material {
    val mat = FluidMaterial(name, textureType, color, tier)
    mat(builder)
    return mat.build()
}

@JvmOverloads
fun groupMaterial(name: String, builder: GroupMaterial.() -> Unit = { }): Material {
    val mat = GroupMaterial(name)
    mat(builder)
    return mat.build()
}

@JvmOverloads
fun transitionMaterial(name: String, endMaterial: () -> Material, neededAmount: Int, builder: TransitionMaterial.() -> Unit = { }): Material {
    val mat = TransitionMaterial(name, endMaterial, neededAmount)
    mat(builder)
    return mat.build()
}

@JvmOverloads
fun itemType(name: String, requirement: (Material) -> Boolean, constructor: (Material, ItemType) -> Item = ::MaterialItem, builder: ItemType.() -> Unit = { }): ObjectType<*, *> {
    val type = ItemType(name, requirement, constructor)
    type(builder)
    return type.build()
}

@JvmOverloads
fun blockType(name: String, requirement: (Material) -> Boolean, properties: Block.Properties, constructor: (Material, BlockType) -> Block = ::MaterialBlock, itemConstructor: (Material, BlockType) -> Item = ::MaterialBlockItem, builder: BlockType.() -> Unit = { }): ObjectType<*, *> {
    val type = BlockType(name, requirement, properties, constructor, itemConstructor)
    type(builder)
    return type.build()
}

@JvmOverloads
fun fluidType(name: String, requirement: (Material) -> Boolean, fluidConstructor: (Material, FluidType) -> IFluidPairHolder = ::MaterialFluidHolder, blockConstructor: (Material, FluidType) -> FlowingFluidBlock = ::MaterialFluidBlock, bucketConstructor: (Material, FluidType) -> BucketItem = ::MaterialBucketItem, builder: FluidType.() -> Unit = { }): ObjectType<*, *> {
    val type = FluidType(name, requirement, fluidConstructor, blockConstructor, bucketConstructor)
    type(builder)
    return type.build()
}