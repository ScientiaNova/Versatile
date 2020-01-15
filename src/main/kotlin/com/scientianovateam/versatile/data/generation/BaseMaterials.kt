package com.scientianovateam.versatile.data.generation

import com.scientianovateam.versatile.materialsystem.addition.BaseTextureTypes
import com.scientianovateam.versatile.materialsystem.builders.*
import com.scientianovateam.versatile.materialsystem.properties.BlockCompaction
import com.scientianovateam.versatile.materialsystem.properties.CompoundType
import net.minecraft.data.DataGenerator

fun DataGenerator.addMaterials() = materials {
    +ingotMaterial("brick") {
        tier = 1
        color = 0xffb55c42
        blockCompaction = BlockCompaction.FROM_2X2
        compoundType = CompoundType.CHEMICAL
        malleable = false
    }
    +ingotMaterial("nether_brick") {
        tier = 1
        color = 0xff472a30
        blockCompaction = BlockCompaction.FROM_2X2
        compoundType = CompoundType.CHEMICAL
        malleable = false
    }
    +ingotMaterial("iron") {
        tier = 1
        textureSet = BaseTextureTypes.ROUGH
        element = "iron"
        unrefinedColor = 0xff947664
        toolTier = "iron"
        armorTier = "iron"
        liquidTemperature = 1538
        gasTemperature = 2862
        hasOre = true
    }
    +ingotMaterial("gold") {
        tier = 2
        color = 0xfffad64a
        textureSet = BaseTextureTypes.SHINY
        element = "gold"
        toolTier = "gold"
        armorTier = "gold"
        liquidTemperature = 1064
        gasTemperature = 2700
        hasOre = true
    }
    +gemMaterial("coal") {
        color = 0xff1a1a1a
        textureSet = BaseTextureTypes.FUEL
        burnTime = 1600
        element = "carbon"
        hasOre = true
    }
    +gemMaterial("charcoal") {
        color = 0xff443e33
        textureSet = BaseTextureTypes.FUEL
        burnTime = 1600
        element = "carbon"
    }
    +gemMaterial("flint") {
        color = 0xff222020
        textureSet = BaseTextureTypes.SHARP
        blockCompaction = BlockCompaction.NONE
    }
    +gemMaterial("lapis") {
        color = 0xff2351be
        hasOre = true
    }
    +gemMaterial("quartz") {
        color = 0xffe8dfd0
        textureSet = BaseTextureTypes.CRYSTAL
        blockCompaction = BlockCompaction.FROM_2X2
        hasOre = true
    }
    +gemMaterial("diamond") {
        tier = 2
        color = 0xff34ebe3
        textureSet = BaseTextureTypes.PENTAGONAL
        element = "carbon"
        toolTier = "diamond"
        armorTier = "diamond"
        hasOre = true
    }
    +gemMaterial("emerald") {
        tier = 2
        color = 0xff08ad2c
        textureSet = BaseTextureTypes.OCTAGONAL
        hasOre = true
    }
    +dustMaterial("wooden") {
        tier = -1
        color = 0xff87672c
        textureSet = BaseTextureTypes.FINE
        burnTime = 200
        toolTier = "wood"
    }
    +dustMaterial("stone") {
        color = 0xffb1b0ae
        textureSet = BaseTextureTypes.FINE
        toolTier = "stone"
    }
    +dustMaterial("bone") {
        color = 0xfffcfaed
        rodOutputCount = 3
    }
    +dustMaterial("blaze") {
        color = 0xffffc20c
        rodOutputCount = 3
    }
    +dustMaterial("redstone") {
        tier = 1
        color = 0xfffc1a19
        hasOre = true
    }
    +dustMaterial("glowstone") {
        tier = 1
        color = 0xfffcbe60
        blockCompaction = BlockCompaction.FROM_2X2
    }
    +dustMaterial("obsidian") {
        tier = 1
        color = 0xff3c2a53
        textureSet = BaseTextureTypes.FINE
    }
    +liquidMaterial("water") {
        color = 0xff3e4ac6
        textureSet = BaseTextureTypes.FLUID
    }
    +liquidMaterial("lava") {
        color = 0xffc54c13
        textureSet = BaseTextureTypes.FLUID
    }
}