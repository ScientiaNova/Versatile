package com.emosewapixel.pixellib.materialsystem.addition

import com.emosewapixel.pixellib.fluids.FluidPairHolder
import com.emosewapixel.pixellib.materialsystem.elements.BaseElements
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialFluids
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.properties.BlockCompaction
import com.emosewapixel.pixellib.materialsystem.properties.CompoundType
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluids
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.ItemTier
import net.minecraft.item.Items
import net.minecraftforge.eventbus.api.EventPriority

//This class is used for registering the vanilla materials and object types
@MaterialRegistry(EventPriority.HIGH)
object BaseMaterials {
    @JvmField
    val BRICK = ingotMaterial("brick") {
        tier = 1
        color = 0xb55c42
        blockCompaction = BlockCompaction.FROM_2X2
        typeBlacklist += BaseObjTypes.NUGGET
        compoundType = CompoundType.CHEMICAL
    }
    @JvmField
    val NETHER_BRICK = ingotMaterial("nether_brick") {
        tier = 1
        color = 0x472a30
        blockCompaction = BlockCompaction.FROM_2X2
        typeBlacklist += BaseObjTypes.NUGGET
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
        MaterialItems.addItem(COAL, BaseObjTypes.GEM, Items.COAL)
        MaterialBlocks.addBlock(COAL, BaseObjTypes.BLOCK, Blocks.COAL_BLOCK)
        MaterialBlocks.addBlock(COAL, BaseObjTypes.ORE, Blocks.COAL_ORE)

        MaterialItems.addItem(CHARCOAL, BaseObjTypes.GEM, Items.CHARCOAL)

        MaterialItems.addItem(IRON, BaseObjTypes.INGOT, Items.IRON_INGOT)
        MaterialItems.addItem(IRON, BaseObjTypes.NUGGET, Items.IRON_NUGGET)
        MaterialBlocks.addBlock(IRON, BaseObjTypes.BLOCK, Blocks.IRON_BLOCK)
        MaterialBlocks.addBlock(IRON, BaseObjTypes.ORE, Blocks.IRON_ORE)

        MaterialItems.addItem(GOLD, BaseObjTypes.INGOT, Items.GOLD_INGOT)
        MaterialItems.addItem(GOLD, BaseObjTypes.NUGGET, Items.GOLD_NUGGET)
        MaterialBlocks.addBlock(GOLD, BaseObjTypes.BLOCK, Blocks.GOLD_BLOCK)
        MaterialBlocks.addBlock(GOLD, BaseObjTypes.ORE, Blocks.GOLD_ORE)

        MaterialItems.addItem(LAPIS, BaseObjTypes.GEM, Items.LAPIS_LAZULI)
        MaterialBlocks.addBlock(LAPIS, BaseObjTypes.BLOCK, Blocks.LAPIS_BLOCK)
        MaterialBlocks.addBlock(LAPIS, BaseObjTypes.ORE, Blocks.LAPIS_ORE)

        MaterialItems.addItem(DIAMOND, BaseObjTypes.GEM, Items.DIAMOND)
        MaterialBlocks.addBlock(DIAMOND, BaseObjTypes.BLOCK, Blocks.DIAMOND_BLOCK)
        MaterialBlocks.addBlock(DIAMOND, BaseObjTypes.ORE, Blocks.DIAMOND_ORE)

        MaterialItems.addItem(EMERALD, BaseObjTypes.GEM, Items.EMERALD)
        MaterialBlocks.addBlock(EMERALD, BaseObjTypes.BLOCK, Blocks.EMERALD_BLOCK)
        MaterialBlocks.addBlock(EMERALD, BaseObjTypes.ORE, Blocks.EMERALD_ORE)

        MaterialItems.addItem(QUARTZ, BaseObjTypes.GEM, Items.QUARTZ)
        MaterialBlocks.addBlock(QUARTZ, BaseObjTypes.BLOCK, Blocks.QUARTZ_BLOCK)
        MaterialBlocks.addBlock(QUARTZ, BaseObjTypes.ORE, Blocks.NETHER_QUARTZ_ORE)

        MaterialItems.addItem(REDSTONE, BaseObjTypes.DUST, Items.REDSTONE)
        MaterialBlocks.addBlock(REDSTONE, BaseObjTypes.BLOCK, Blocks.REDSTONE_BLOCK)
        MaterialBlocks.addBlock(REDSTONE, BaseObjTypes.ORE, Blocks.REDSTONE_ORE)

        MaterialItems.addItem(GLOWSTONE, BaseObjTypes.DUST, Items.GLOWSTONE_DUST)
        MaterialBlocks.addBlock(GLOWSTONE, BaseObjTypes.BLOCK, Blocks.GLOWSTONE)

        MaterialItems.addItem(BRICK, BaseObjTypes.INGOT, Items.BRICK)
        MaterialBlocks.addBlock(BRICK, BaseObjTypes.BLOCK, Blocks.BRICKS)

        MaterialItems.addItem(NETHER_BRICK, BaseObjTypes.INGOT, Items.NETHER_BRICK)
        MaterialBlocks.addBlock(NETHER_BRICK, BaseObjTypes.BLOCK, Blocks.NETHER_BRICKS)

        MaterialItems.addItem(FLINT, BaseObjTypes.GEM, Items.FLINT)

        MaterialItems.addItem(BONE, BaseObjTypes.DUST, Items.BONE_MEAL)
        MaterialBlocks.addBlock(BONE, BaseObjTypes.BLOCK, Blocks.BONE_BLOCK)

        MaterialItems.addItem(BLAZE, BaseObjTypes.DUST, Items.BLAZE_POWDER)

        MaterialFluids.addFluidPair(WATER, BaseObjTypes.FLUID, FluidPairHolder(Fluids.WATER, Fluids.FLOWING_WATER))
        MaterialBlocks.addBlock(WATER, BaseObjTypes.FLUID, Blocks.WATER)
        MaterialItems.addItem(WATER, BaseObjTypes.FLUID, Items.WATER_BUCKET)

        MaterialFluids.addFluidPair(LAVA, BaseObjTypes.FLUID, FluidPairHolder(Fluids.LAVA, Fluids.FLOWING_LAVA))
        MaterialBlocks.addBlock(LAVA, BaseObjTypes.FLUID, Blocks.LAVA)
        MaterialItems.addItem(LAVA, BaseObjTypes.FLUID, Items.LAVA_BUCKET)
    }
}