@file:JvmName("BaseMaterials")

package com.scientianova.versatile.materialsystem.addition

import com.scientianova.versatile.fluids.FluidPairHolder
import com.scientianova.versatile.materialsystem.builders.dustMaterial
import com.scientianova.versatile.materialsystem.builders.fluidMaterial
import com.scientianova.versatile.materialsystem.builders.gemMaterial
import com.scientianova.versatile.materialsystem.builders.ingotMaterial
import com.scientianova.versatile.materialsystem.properties.BlockCompaction
import com.scientianova.versatile.materialsystem.properties.CompoundType
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluids
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.ItemTier
import net.minecraft.item.Items

val BRICK = ingotMaterial("brick") {
    tier = 1
    color = 0xb55c42
    blockCompaction = BlockCompaction.FROM_2X2
    compoundType = CompoundType.CHEMICAL
    malleable = false
}
val NETHER_BRICK = ingotMaterial("nether_brick") {
    tier = 1
    color = 0x472a30
    blockCompaction = BlockCompaction.FROM_2X2
    compoundType = CompoundType.CHEMICAL
    malleable = false
}
val IRON = ingotMaterial("iron") {
    tier = 1
    textureSet = BaseTextureSets.ROUGH
    element = BaseElements.IRON
    unrefinedColor = 0x947664
    itemTier = ItemTier.IRON
    armorMaterial = ArmorMaterial.IRON
    liquidTemperature = 1538
    gasTemperature = 2862
    hasOre = true
}
val GOLD = ingotMaterial("gold") {
    tier = 2
    color = 0xfad64a
    textureSet = BaseTextureSets.SHINY
    element = BaseElements.GOLD
    itemTier = ItemTier.GOLD
    armorMaterial = ArmorMaterial.GOLD
    liquidTemperature = 1064
    gasTemperature = 2700
    hasOre = true
}
val COAL = gemMaterial("coal") {
    color = 0x1a1a1a
    textureSet = BaseTextureSets.FUEL
    standardBurnTime = 1600
    element = BaseElements.CARBON
    hasOre = true
}
val CHARCOAL = gemMaterial("charcoal") {
    color = 0x443e33
    textureSet = BaseTextureSets.FUEL
    standardBurnTime = 1600
    element = BaseElements.CARBON
}
val FLINT = gemMaterial("flint") {
    color = 0x222020
    textureSet = BaseTextureSets.SHARP
    blockCompaction = BlockCompaction.NONE
}
val LAPIS = gemMaterial("lapis") {
    color = 0x2351be
    hasOre = true
}
val QUARTZ = gemMaterial("quartz") {
    color = 0xe8dfd0
    textureSet = BaseTextureSets.CRYSTAL
    blockCompaction = BlockCompaction.FROM_2X2
    hasOre = true
}
val DIAMOND = gemMaterial("diamond") {
    tier = 2
    color = 0x34ebe3
    textureSet = BaseTextureSets.PENTAGONAL
    element = BaseElements.CARBON
    itemTier = ItemTier.DIAMOND
    armorMaterial = ArmorMaterial.DIAMOND
    hasOre = true
}
val EMERALD = gemMaterial("emerald") {
    tier = 2
    color = 0x08ad2c
    textureSet = BaseTextureSets.OCTAGONAL
    hasOre = true
}
val WOODEN = dustMaterial("wooden") {
    tier = -1
    color = 0x87672c
    textureSet = BaseTextureSets.FINE
    standardBurnTime = 200
    itemTier = ItemTier.WOOD
}
val STONE = dustMaterial("stone") {
    color = 0xb1b0ae
    textureSet = BaseTextureSets.FINE
    itemTier = ItemTier.STONE
}
val BONE = dustMaterial("bone") {
    color = 0xfcfaed
    rodOutputCount = 3
}
val BLAZE = dustMaterial("blaze") {
    color = 0xffc20c
    rodOutputCount = 3
}
val REDSTONE = dustMaterial("redstone") {
    tier = 1
    color = 0xfc1a19
    hasOre = true
}
val GLOWSTONE = dustMaterial("glowstone") {
    tier = 1
    color = 0xfcbe60
    blockCompaction = BlockCompaction.FROM_2X2
}
val OBSIDIAN = dustMaterial("obsidian") {
    tier = 1
    color = 0x3c2a53
    textureSet = BaseTextureSets.FINE
}
val WATER = fluidMaterial("water") {
    color = 0x3e4ac6
    textureSet = BaseTextureSets.FLUID
    liquidTemperature = 300
    gasNames = listOf("steam")
    gasTemperature = 373
    gasColor = -1
}
val LAVA = fluidMaterial("lava") {
    color = 0xc54c13
    textureSet = BaseTextureSets.FLUID
}

fun addVanilla() {
    GEM_FORM[COAL]?.set(ITEM, Items.COAL)
    BLOCK_FORM[COAL]?.set(BLOCK, Blocks.COAL_BLOCK)
    ORE_FORM[COAL]?.set(BLOCK, Blocks.COAL_ORE)

    GEM_FORM[CHARCOAL]?.set(ITEM, Items.CHARCOAL)

    INGOT_FORM[IRON]?.set(ITEM, Items.IRON_INGOT)
    NUGGET_FORM[IRON]?.set(ITEM, Items.IRON_NUGGET)
    BLOCK_FORM[IRON]?.set(BLOCK, Blocks.IRON_BLOCK)
    ORE_FORM[IRON]?.set(BLOCK, Blocks.COAL_ORE)

    INGOT_FORM[GOLD]?.set(ITEM, Items.GOLD_INGOT)
    NUGGET_FORM[GOLD]?.set(ITEM, Items.GOLD_NUGGET)
    BLOCK_FORM[GOLD]?.set(BLOCK, Blocks.GOLD_BLOCK)
    ORE_FORM[GOLD]?.set(BLOCK, Blocks.GOLD_ORE)

    GEM_FORM[LAPIS]?.set(ITEM, Items.LAPIS_LAZULI)
    BLOCK_FORM[LAPIS]?.set(BLOCK, Blocks.LAPIS_BLOCK)
    ORE_FORM[LAPIS]?.set(BLOCK, Blocks.LAPIS_ORE)

    GEM_FORM[DIAMOND]?.set(ITEM, Items.DIAMOND)
    BLOCK_FORM[DIAMOND]?.set(BLOCK, Blocks.DIAMOND_BLOCK)
    ORE_FORM[DIAMOND]?.set(BLOCK, Blocks.DIAMOND_ORE)

    GEM_FORM[EMERALD]?.set(ITEM, Items.EMERALD)
    BLOCK_FORM[EMERALD]?.set(BLOCK, Blocks.EMERALD_BLOCK)
    ORE_FORM[EMERALD]?.set(BLOCK, Blocks.EMERALD_ORE)

    GEM_FORM[QUARTZ]?.set(ITEM, Items.QUARTZ)
    BLOCK_FORM[QUARTZ]?.set(BLOCK, Blocks.QUARTZ_BLOCK)
    ORE_FORM[QUARTZ]?.set(BLOCK, Blocks.NETHER_QUARTZ_ORE)

    DUST_FORM[REDSTONE]?.set(ITEM, Items.REDSTONE)
    BLOCK_FORM[REDSTONE]?.set(BLOCK, Blocks.REDSTONE_BLOCK)
    ORE_FORM[REDSTONE]?.set(BLOCK, Blocks.REDSTONE_ORE)

    DUST_FORM[GLOWSTONE]?.set(ITEM, Items.GLOWSTONE_DUST)
    BLOCK_FORM[GLOWSTONE]?.set(BLOCK, Blocks.GLOWSTONE)

    INGOT_FORM[BRICK]?.set(ITEM, Items.BRICK)
    BLOCK_FORM[BRICK]?.set(BLOCK, Blocks.BRICKS)

    INGOT_FORM[NETHER_BRICK]?.set(ITEM, Items.NETHER_BRICK)
    BLOCK_FORM[NETHER_BRICK]?.set(BLOCK, Blocks.NETHER_BRICKS)

    GEM_FORM[FLINT]?.set(ITEM, Items.FLINT)

    DUST_FORM[BONE]?.set(ITEM, Items.BONE_MEAL)
    BLOCK_FORM[BONE]?.set(BLOCK, Blocks.BONE_BLOCK)

    DUST_FORM[BLAZE]?.set(ITEM, Items.BLAZE_POWDER)

    LIQUID_FORM[WATER]?.set(ITEM, Items.WATER_BUCKET)
    LIQUID_FORM[WATER]?.set(BLOCK, Blocks.WATER)
    LIQUID_FORM[WATER]?.set(FLUID, FluidPairHolder(Fluids.WATER, Fluids.FLOWING_WATER))

    LIQUID_FORM[LAVA]?.set(ITEM, Items.LAVA_BUCKET)
    LIQUID_FORM[LAVA]?.set(BLOCK, Blocks.LAVA)
    LIQUID_FORM[LAVA]?.set(FLUID, FluidPairHolder(Fluids.LAVA, Fluids.FLOWING_LAVA))
}