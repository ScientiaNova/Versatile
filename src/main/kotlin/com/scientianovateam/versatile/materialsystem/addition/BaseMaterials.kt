package com.scientianovateam.versatile.materialsystem.addition

import com.scientianovateam.versatile.common.registry.VersatileRegistry
import com.scientianovateam.versatile.fluids.FluidPairHolder
import com.scientianovateam.versatile.materialsystem.builders.dustMaterial
import com.scientianovateam.versatile.materialsystem.builders.fluidMaterial
import com.scientianovateam.versatile.materialsystem.builders.gemMaterial
import com.scientianovateam.versatile.materialsystem.builders.ingotMaterial
import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.materialsystem.lists.MaterialFluids
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.properties.BlockCompaction
import com.scientianovateam.versatile.materialsystem.properties.CompoundType
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluids
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.ItemTier
import net.minecraft.item.Items
import net.minecraftforge.eventbus.api.EventPriority

//This class is used for registering the vanilla materials and object types
@VersatileRegistry(EventPriority.HIGH)
object BaseMaterials {
    @JvmField
    val BRICK = ingotMaterial("brick") {
        tier = 1
        color = 0xb55c42
        blockCompaction = BlockCompaction.FROM_2X2
        typeBlacklist += BaseForms.NUGGET
        compoundType = CompoundType.CHEMICAL
    }
    @JvmField
    val NETHER_BRICK = ingotMaterial("nether_brick") {
        tier = 1
        color = 0x472a30
        blockCompaction = BlockCompaction.FROM_2X2
        typeBlacklist += BaseForms.NUGGET
        compoundType = CompoundType.CHEMICAL
    }
    @JvmField
    val IRON = ingotMaterial("iron") {
        tier = 1
        textureType = BaseTextureTypes.ROUGH
        element = BaseElements.IRON
        unrefinedColor = 0x947664
        itemTier = ItemTier.IRON
        armorMaterial = ArmorMaterial.IRON
        fluidTemperature = 1538
        boilingTemperature = 2862
        hasOre = true
    }
    @JvmField
    val GOLD = ingotMaterial("gold") {
        tier = 2
        color = 0xfad64a
        textureType = BaseTextureTypes.SHINY
        element = BaseElements.GOLD
        itemTier = ItemTier.GOLD
        armorMaterial = ArmorMaterial.GOLD
        fluidTemperature = 1064
        boilingTemperature = 2700
        hasOre = true
    }
    @JvmField
    val COAL = gemMaterial("coal") {
        color = 0x1a1a1a
        textureType = BaseTextureTypes.FUEL
        standardBurnTime = 1600
        element = BaseElements.CARBON
        hasOre = true
    }
    @JvmField
    val CHARCOAL = gemMaterial("charcoal") {
        color = 0x443e33
        textureType = BaseTextureTypes.FUEL
        standardBurnTime = 1600
        element = BaseElements.CARBON
    }
    @JvmField
    val FLINT = gemMaterial("flint") {
        color = 0x222020
        textureType = BaseTextureTypes.SHARP
        blockCompaction = BlockCompaction.NONE
    }
    @JvmField
    val LAPIS = gemMaterial("lapis") {
        color = 0x2351be
        hasOre = true
    }
    @JvmField
    val QUARTZ = gemMaterial("quartz") {
        color = 0xe8dfd0
        textureType = BaseTextureTypes.CRYSTAL
        blockCompaction = BlockCompaction.FROM_2X2
        hasOre = true
    }
    @JvmField
    val DIAMOND = gemMaterial("diamond") {
        tier = 2
        color = 0x34ebe3
        textureType = BaseTextureTypes.PENTAGONAL
        element = BaseElements.CARBON
        itemTier = ItemTier.DIAMOND
        armorMaterial = ArmorMaterial.DIAMOND
        hasOre = true
    }
    @JvmField
    val EMERALD = gemMaterial("emerald") {
        tier = 2
        color = 0x08ad2c
        textureType = BaseTextureTypes.OCTAGONAL
        hasOre = true
    }
    @JvmField
    val WOODEN = dustMaterial("wooden") {
        tier = -1
        color = 0x87672c
        textureType = BaseTextureTypes.FINE
        standardBurnTime = 200
        itemTier = ItemTier.WOOD
    }
    @JvmField
    val STONE = dustMaterial("stone") {
        color = 0xb1b0ae
        textureType = BaseTextureTypes.FINE
        itemTier = ItemTier.STONE
    }
    @JvmField
    val BONE = dustMaterial("bone") {
        color = 0xfcfaed
        rodOutputCount = 3
    }
    @JvmField
    val BLAZE = dustMaterial("blaze") {
        color = 0xffc20c
        rodOutputCount = 3
    }
    @JvmField
    val REDSTONE = dustMaterial("redstone") {
        tier = 1
        color = 0xfc1a19
        hasOre = true
    }
    @JvmField
    val GLOWSTONE = dustMaterial("glowstone") {
        tier = 1
        color = 0xfcbe60
        blockCompaction = BlockCompaction.FROM_2X2
    }
    @JvmField
    val OBSIDIAN = dustMaterial("obsidian") {
        tier = 1
        color = 0x3c2a53
        textureType = BaseTextureTypes.FINE
    }
    @JvmField
    val WATER = fluidMaterial("water") {
        color = 0x3e4ac6
        textureType = BaseTextureTypes.FLUID
    }
    @JvmField
    val LAVA = fluidMaterial("lava") {
        color = 0xc54c13
        textureType = BaseTextureTypes.FLUID
    }

    init {
        MaterialItems.addItem(COAL, BaseForms.GEM, Items.COAL)
        MaterialBlocks.addBlock(COAL, BaseForms.BLOCK, Blocks.COAL_BLOCK)
        MaterialBlocks.addBlock(COAL, BaseForms.ORE, Blocks.COAL_ORE)

        MaterialItems.addItem(CHARCOAL, BaseForms.GEM, Items.CHARCOAL)

        MaterialItems.addItem(IRON, BaseForms.INGOT, Items.IRON_INGOT)
        MaterialItems.addItem(IRON, BaseForms.NUGGET, Items.IRON_NUGGET)
        MaterialBlocks.addBlock(IRON, BaseForms.BLOCK, Blocks.IRON_BLOCK)
        MaterialBlocks.addBlock(IRON, BaseForms.ORE, Blocks.IRON_ORE)

        MaterialItems.addItem(GOLD, BaseForms.INGOT, Items.GOLD_INGOT)
        MaterialItems.addItem(GOLD, BaseForms.NUGGET, Items.GOLD_NUGGET)
        MaterialBlocks.addBlock(GOLD, BaseForms.BLOCK, Blocks.GOLD_BLOCK)
        MaterialBlocks.addBlock(GOLD, BaseForms.ORE, Blocks.GOLD_ORE)

        MaterialItems.addItem(LAPIS, BaseForms.GEM, Items.LAPIS_LAZULI)
        MaterialBlocks.addBlock(LAPIS, BaseForms.BLOCK, Blocks.LAPIS_BLOCK)
        MaterialBlocks.addBlock(LAPIS, BaseForms.ORE, Blocks.LAPIS_ORE)

        MaterialItems.addItem(DIAMOND, BaseForms.GEM, Items.DIAMOND)
        MaterialBlocks.addBlock(DIAMOND, BaseForms.BLOCK, Blocks.DIAMOND_BLOCK)
        MaterialBlocks.addBlock(DIAMOND, BaseForms.ORE, Blocks.DIAMOND_ORE)

        MaterialItems.addItem(EMERALD, BaseForms.GEM, Items.EMERALD)
        MaterialBlocks.addBlock(EMERALD, BaseForms.BLOCK, Blocks.EMERALD_BLOCK)
        MaterialBlocks.addBlock(EMERALD, BaseForms.ORE, Blocks.EMERALD_ORE)

        MaterialItems.addItem(QUARTZ, BaseForms.GEM, Items.QUARTZ)
        MaterialBlocks.addBlock(QUARTZ, BaseForms.BLOCK, Blocks.QUARTZ_BLOCK)
        MaterialBlocks.addBlock(QUARTZ, BaseForms.ORE, Blocks.NETHER_QUARTZ_ORE)

        MaterialItems.addItem(REDSTONE, BaseForms.DUST, Items.REDSTONE)
        MaterialBlocks.addBlock(REDSTONE, BaseForms.BLOCK, Blocks.REDSTONE_BLOCK)
        MaterialBlocks.addBlock(REDSTONE, BaseForms.ORE, Blocks.REDSTONE_ORE)

        MaterialItems.addItem(GLOWSTONE, BaseForms.DUST, Items.GLOWSTONE_DUST)
        MaterialBlocks.addBlock(GLOWSTONE, BaseForms.BLOCK, Blocks.GLOWSTONE)

        MaterialItems.addItem(BRICK, BaseForms.INGOT, Items.BRICK)
        MaterialBlocks.addBlock(BRICK, BaseForms.BLOCK, Blocks.BRICKS)

        MaterialItems.addItem(NETHER_BRICK, BaseForms.INGOT, Items.NETHER_BRICK)
        MaterialBlocks.addBlock(NETHER_BRICK, BaseForms.BLOCK, Blocks.NETHER_BRICKS)

        MaterialItems.addItem(FLINT, BaseForms.GEM, Items.FLINT)

        MaterialItems.addItem(BONE, BaseForms.DUST, Items.BONE_MEAL)
        MaterialBlocks.addBlock(BONE, BaseForms.BLOCK, Blocks.BONE_BLOCK)

        MaterialItems.addItem(BLAZE, BaseForms.DUST, Items.BLAZE_POWDER)

        MaterialFluids.addFluidPair(WATER, BaseForms.FLUID, FluidPairHolder(Fluids.WATER, Fluids.FLOWING_WATER))
        MaterialBlocks.addBlock(WATER, BaseForms.FLUID, Blocks.WATER)
        MaterialItems.addItem(WATER, BaseForms.FLUID, Items.WATER_BUCKET)

        MaterialFluids.addFluidPair(LAVA, BaseForms.FLUID, FluidPairHolder(Fluids.LAVA, Fluids.FLOWING_LAVA))
        MaterialBlocks.addBlock(LAVA, BaseForms.FLUID, Blocks.LAVA)
        MaterialItems.addItem(LAVA, BaseForms.FLUID, Items.LAVA_BUCKET)
    }
}