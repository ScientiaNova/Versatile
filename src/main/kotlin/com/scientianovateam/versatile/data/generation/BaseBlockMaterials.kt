package com.scientianovateam.versatile.data.generation

import com.scientianovateam.versatile.blocks.properties.BlockMaterialWrapper
import com.scientianovateam.versatile.common.extensions.toResLoc
import net.minecraft.block.material.MaterialColor
import net.minecraft.block.material.PushReaction
import net.minecraft.data.DataGenerator

fun DataGenerator.addBlockMaterials() = blockMaterials {
    +BlockMaterialWrapper(
            registryName = "air".toResLoc(),
            color = MaterialColor.AIR,
            blocksMovement = false,
            opaque = false,
            solid = false,
            replaceable = true
    )
    +BlockMaterialWrapper(
            registryName = "structure_void".toResLoc(),
            color = MaterialColor.AIR,
            blocksMovement = false,
            opaque = false,
            solid = false,
            replaceable = true
    )
    +BlockMaterialWrapper(
            registryName = "portal".toResLoc(),
            color = MaterialColor.AIR,
            blocksMovement = false,
            opaque = false,
            solid = false,
            pushReaction = PushReaction.BLOCK
    )
    +BlockMaterialWrapper(
            registryName = "carpet".toResLoc(),
            color = MaterialColor.WOOD,
            blocksMovement = false,
            opaque = false,
            solid = false,
            flammable = true
    )
    +BlockMaterialWrapper(
            registryName = "plant".toResLoc(),
            color = MaterialColor.FOLIAGE,
            blocksMovement = false,
            opaque = false,
            solid = false,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "ocean_plant".toResLoc(),
            color = MaterialColor.WATER,
            blocksMovement = false,
            opaque = false,
            solid = false,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "tall_plant".toResLoc(),
            color = MaterialColor.FOLIAGE,
            blocksMovement = false,
            opaque = false,
            solid = false,
            flammable = true,
            replaceable = true,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "sea_grass".toResLoc(),
            color = MaterialColor.WATER,
            blocksMovement = false,
            opaque = false,
            solid = false,
            replaceable = true,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "water".toResLoc(),
            color = MaterialColor.WATER,
            blocksMovement = false,
            liquid = true,
            opaque = false,
            solid = false,
            replaceable = true,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "bubble_column".toResLoc(),
            color = MaterialColor.WATER,
            blocksMovement = false,
            liquid = true,
            opaque = false,
            solid = false,
            replaceable = true,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "lava".toResLoc(),
            color = MaterialColor.TNT,
            blocksMovement = false,
            liquid = true,
            opaque = false,
            solid = false,
            replaceable = true,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "snow".toResLoc(),
            color = MaterialColor.SNOW,
            blocksMovement = false,
            liquid = true,
            opaque = false,
            solid = false,
            replaceable = true,
            requiresTool = true,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "fire".toResLoc(),
            color = MaterialColor.AIR,
            blocksMovement = false,
            opaque = false,
            solid = false,
            replaceable = true,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "miscellaneous".toResLoc(),
            color = MaterialColor.AIR,
            blocksMovement = false,
            opaque = false,
            solid = false,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "web".toResLoc(),
            color = MaterialColor.WOOL,
            blocksMovement = false,
            opaque = false,
            requiresTool = true,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "redstone_light".toResLoc(),
            color = MaterialColor.AIR
    )
    +BlockMaterialWrapper(
            registryName = "clay".toResLoc(),
            color = MaterialColor.CLAY
    )
    +BlockMaterialWrapper(
            registryName = "earth".toResLoc(),
            color = MaterialColor.DIRT
    )
    +BlockMaterialWrapper(
            registryName = "organic".toResLoc(),
            color = MaterialColor.GRASS
    )
    +BlockMaterialWrapper(
            registryName = "packed_ice".toResLoc(),
            color = MaterialColor.ICE
    )
    +BlockMaterialWrapper(
            registryName = "sand".toResLoc(),
            color = MaterialColor.SAND
    )
    +BlockMaterialWrapper(
            registryName = "sponge".toResLoc(),
            color = MaterialColor.YELLOW
    )
    +BlockMaterialWrapper(
            registryName = "shulker".toResLoc(),
            color = MaterialColor.PURPLE
    )
    +BlockMaterialWrapper(
            registryName = "wood".toResLoc(),
            color = MaterialColor.WOOD,
            flammable = true
    )
    +BlockMaterialWrapper(
            registryName = "bamboo_sapling".toResLoc(),
            color = MaterialColor.WOOD,
            blocksMovement = false,
            flammable = true,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "bamboo".toResLoc(),
            color = MaterialColor.WOOD,
            flammable = true,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "wool".toResLoc(),
            color = MaterialColor.WOOL,
            flammable = true
    )
    +BlockMaterialWrapper(
            registryName = "tnt".toResLoc(),
            color = MaterialColor.TNT,
            opaque = false,
            flammable = true
    )
    +BlockMaterialWrapper(
            registryName = "leaves".toResLoc(),
            color = MaterialColor.FOLIAGE,
            opaque = false,
            flammable = true,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "glass".toResLoc(),
            color = MaterialColor.AIR,
            opaque = false
    )
    +BlockMaterialWrapper(
            registryName = "ice".toResLoc(),
            color = MaterialColor.ICE,
            opaque = false
    )
    +BlockMaterialWrapper(
            registryName = "cactus".toResLoc(),
            color = MaterialColor.FOLIAGE,
            opaque = false,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "rock".toResLoc(),
            color = MaterialColor.STONE,
            requiresTool = true
    )
    +BlockMaterialWrapper(
            registryName = "iron".toResLoc(),
            color = MaterialColor.IRON,
            requiresTool = true
    )
    +BlockMaterialWrapper(
            registryName = "snow_block".toResLoc(),
            color = MaterialColor.SNOW,
            requiresTool = true
    )
    +BlockMaterialWrapper(
            registryName = "anvil".toResLoc(),
            color = MaterialColor.IRON,
            requiresTool = true,
            pushReaction = PushReaction.BLOCK
    )
    +BlockMaterialWrapper(
            registryName = "barrier".toResLoc(),
            color = MaterialColor.AIR,
            requiresTool = true,
            pushReaction = PushReaction.BLOCK
    )
    +BlockMaterialWrapper(
            registryName = "piston".toResLoc(),
            color = MaterialColor.STONE,
            pushReaction = PushReaction.BLOCK
    )
    +BlockMaterialWrapper(
            registryName = "coral".toResLoc(),
            color = MaterialColor.FOLIAGE,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "gourd".toResLoc(),
            color = MaterialColor.FOLIAGE,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "dragon_egg".toResLoc(),
            color = MaterialColor.FOLIAGE,
            pushReaction = PushReaction.DESTROY
    )
    +BlockMaterialWrapper(
            registryName = "cake".toResLoc(),
            color = MaterialColor.AIR,
            pushReaction = PushReaction.DESTROY
    )
}