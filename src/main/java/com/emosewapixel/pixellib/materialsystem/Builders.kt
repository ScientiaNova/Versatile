package com.emosewapixel.pixellib.materialsystem

import com.emosewapixel.pixellib.materialsystem.materials.*
import com.emosewapixel.pixellib.materialsystem.materials.utility.GroupMaterial
import com.emosewapixel.pixellib.materialsystem.materials.utility.TransitionMaterial
import com.emosewapixel.pixellib.materialsystem.types.BlockType
import com.emosewapixel.pixellib.materialsystem.types.ItemType
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import com.emosewapixel.pixellib.materialsystem.types.TextureType
import net.minecraft.block.Block
import java.util.function.Predicate

fun dustMaterial(name: String, textureType: TextureType?, color: Int, tier: Int, builder: DustMaterial.() -> Unit): Material {
    val mat = DustMaterial(name, textureType, color, tier)
    mat(builder)
    return mat.build()
}

fun gemMaterial(name: String, textureType: TextureType?, color: Int, tier: Int, builder: GemMaterial.() -> Unit): Material {
    val mat = GemMaterial(name, textureType, color, tier)
    mat(builder)
    return mat.build()
}

fun ingotMaterial(name: String, textureType: TextureType?, color: Int, tier: Int, builder: IngotMaterial.() -> Unit): Material {
    val mat = IngotMaterial(name, textureType, color, tier)
    mat(builder)
    return mat.build()
}

fun fluidMaterial(name: String, textureType: TextureType?, color: Int, tier: Int = 0, builder: FluidMaterial.() -> Unit): Material {
    val mat = FluidMaterial(name, textureType, color, tier)
    mat(builder)
    return mat.build()
}

fun groupMaterial(name: String, builder: GroupMaterial.() -> Unit): Material {
    val mat = GroupMaterial(name)
    mat(builder)
    return mat.build()
}

fun transitionMaterial(name: String, endMaterial: () -> Material, neededAmount: Int, builder: TransitionMaterial.() -> Unit): Material {
    val mat = TransitionMaterial(name, endMaterial, neededAmount)
    mat(builder)
    return mat.build()
}

fun itemType(name: String, requirement: Predicate<Material>, builder: ObjectType.() -> Unit): ObjectType {
    val type = ItemType(name, requirement)
    type(builder)
    return type.build()
}

fun blockType(name: String, requirement: Predicate<Material>, properties: Block.Properties, builder: BlockType.() -> Unit): ObjectType {
    val type = BlockType(name, requirement, properties)
    type(builder)
    return type.build()
}