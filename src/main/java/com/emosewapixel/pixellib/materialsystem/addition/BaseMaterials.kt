package com.emosewapixel.pixellib.materialsystem.addition

import com.emosewapixel.pixellib.extensions.toResLoc
import com.emosewapixel.pixellib.fluids.FluidPairHolder
import com.emosewapixel.pixellib.materialsystem.elements.BaseElements
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialFluids
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.BlockCompaction
import com.emosewapixel.pixellib.materialsystem.materials.CompoundType
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.fluid.Fluids
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.ItemTier
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvents
import net.minecraftforge.eventbus.api.EventPriority

//This class is used for registering the vanilla material and object type tags, materials and object types
@MaterialRegistry(EventPriority.HIGH)
object BaseMaterials {
    //Object Type Tags
    const val HAS_NO_FUEL_VALUE = "has_no_fuel_value"
    const val SINGLE_TEXTURE_TYPE = "1_texture_type"

    //Texture Types
    const val ROUGH = "rough"
    const val REGULAR = "regular"
    const val SHINY = "shiny"
    const val FUEL = "fuel"
    const val PENTAGONAL = "pentagonal"
    const val OCTAGONAL = "octagonal"
    const val CRYSTAL = "crystal"
    const val SHARP = "sharp"
    const val FINE = "fine"
    const val FLUID_TT = "fluid"

    //Object Types
    @JvmField
    val DUST = itemType("dust", com.emosewapixel.pixellib.materialsystem.materials.Material::isDustMaterial) {
        bucketVolume = 144
    }
    @JvmField
    val GEM = itemType("gem", com.emosewapixel.pixellib.materialsystem.materials.Material::isGemMaterial) {
        bucketVolume = 144
    }
    @JvmField
    val INGOT = itemType("ingot", com.emosewapixel.pixellib.materialsystem.materials.Material::isIngotMaterial) {
        bucketVolume = 144
    }
    @JvmField
    val NUGGET = itemType("nugget", com.emosewapixel.pixellib.materialsystem.materials.Material::isIngotMaterial) {
        bucketVolume = 16
    }
    @JvmField
    val BLOCK = blockType("storage_block", { it.isDustMaterial && it.blockCompaction != BlockCompaction.NONE },
            Block.Properties.create(Material.IRON).sound(SoundType.METAL)) {
        buildRegistryName = { ResourceLocation("pixellib:${it.name}_block") }
        bucketVolume = 1296
    }
    @JvmField
    val ORE = blockType("ore", { it.isIngotMaterial && it.hasOre },
            Block.Properties.create(Material.ROCK).sound(SoundType.STONE)) {
        typeTags += HAS_NO_FUEL_VALUE
        color = com.emosewapixel.pixellib.materialsystem.materials.Material::unrefinedColor
        indexBlackList += 1
        bucketVolume = 144
    }
    @JvmField
    val FLUID = fluidType("fluid", com.emosewapixel.pixellib.materialsystem.materials.Material::isFluidMaterial) {
        locationBase = "minecraft:block/water"
        fluidColor = { color(it) or (it.alpha shl 24) }
        overlayTexture = ResourceLocation("minecraft", "block/water_overlay")
        buildRegistryName = { ResourceLocation("pixellib:${it.name}") }
        buildTagName = String::toResLoc
    }
    @JvmField
    val MOLTEN_FLUID = fluidType("molten", { it.isDustMaterial && it.fluidTemperature > 0 }) {
        buildRegistryName = { ResourceLocation("pixellib:molten_${it.name}") }
        buildTagName = { "forge:molten_$it".toResLoc() }
        emptySound = SoundEvents.ITEM_BUCKET_EMPTY_LAVA
        fillSound = SoundEvents.ITEM_BUCKET_FILL_LAVA
    }

    //Materials
    @JvmField
    val BRICK = ingotMaterial("brick") {
        tier = 1
        color = 0xb55c42
        blockCompaction = BlockCompaction.FROM_2X2
        typeBlacklist += NUGGET
        compoundType = CompoundType.CHEMICAL
    }
    @JvmField
    val NETHER_BRICK = ingotMaterial("nether_brick") {
        tier = 1
        color = 0x472a30
        blockCompaction = BlockCompaction.FROM_2X2
        typeBlacklist += NUGGET
        compoundType = CompoundType.CHEMICAL
    }
    @JvmField
    val IRON = ingotMaterial("iron") {
        tier = 1
        textureType = ROUGH
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
        textureType = SHINY
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
        textureType = FUEL
        standardBurnTime = 1600
        element = BaseElements.CARBON
        hasOre = true
    }
    @JvmField
    val CHARCOAL = gemMaterial("charcoal") {
        color = 0x443e33
        textureType = FUEL
        standardBurnTime = 1600
        element = BaseElements.CARBON
    }
    @JvmField
    val FLINT = gemMaterial("flint") {
        color = 0x222020
        textureType = SHARP
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
        textureType = CRYSTAL
        blockCompaction = BlockCompaction.FROM_2X2
        hasOre = true
    }
    @JvmField
    val DIAMOND = gemMaterial("diamond") {
        tier = 2
        color = 0x34ebe3
        textureType = PENTAGONAL
        element = BaseElements.CARBON
        itemTier = ItemTier.DIAMOND
        armorMaterial = ArmorMaterial.DIAMOND
        hasOre = true
    }
    @JvmField
    val EMERALD = gemMaterial("emerald") {
        tier = 2
        color = 0x08ad2c
        textureType = OCTAGONAL
        hasOre = true
    }
    @JvmField
    val WOODEN = dustMaterial("wooden") {
        tier = -1
        color = 0x87672c
        textureType = FINE
        standardBurnTime = 200
        itemTier = ItemTier.WOOD
    }
    @JvmField
    val STONE = dustMaterial("stone") {
        color = 0xb1b0ae
        textureType = FINE
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
        textureType = FINE
    }
    @JvmField
    val WATER = fluidMaterial("water") {
        color = 0x3e4ac6
        textureType = FLUID_TT
    }
    @JvmField
    val LAVA = fluidMaterial("lava") {
        color = 0xc54c13
        textureType = FLUID_TT
    }

    init {
        MaterialItems.addItem(COAL, GEM, Items.COAL)
        MaterialBlocks.addBlock(COAL, BLOCK, Blocks.COAL_BLOCK)
        MaterialBlocks.addBlock(COAL, ORE, Blocks.COAL_ORE)

        MaterialItems.addItem(CHARCOAL, GEM, Items.CHARCOAL)

        MaterialItems.addItem(IRON, INGOT, Items.IRON_INGOT)
        MaterialItems.addItem(IRON, NUGGET, Items.IRON_NUGGET)
        MaterialBlocks.addBlock(IRON, BLOCK, Blocks.IRON_BLOCK)
        MaterialBlocks.addBlock(IRON, ORE, Blocks.IRON_ORE)

        MaterialItems.addItem(GOLD, INGOT, Items.GOLD_INGOT)
        MaterialItems.addItem(GOLD, NUGGET, Items.GOLD_NUGGET)
        MaterialBlocks.addBlock(GOLD, BLOCK, Blocks.GOLD_BLOCK)
        MaterialBlocks.addBlock(GOLD, ORE, Blocks.GOLD_ORE)

        MaterialItems.addItem(LAPIS, GEM, Items.LAPIS_LAZULI)
        MaterialBlocks.addBlock(LAPIS, BLOCK, Blocks.LAPIS_BLOCK)
        MaterialBlocks.addBlock(LAPIS, ORE, Blocks.LAPIS_ORE)

        MaterialItems.addItem(DIAMOND, GEM, Items.DIAMOND)
        MaterialBlocks.addBlock(DIAMOND, BLOCK, Blocks.DIAMOND_BLOCK)
        MaterialBlocks.addBlock(DIAMOND, ORE, Blocks.DIAMOND_ORE)

        MaterialItems.addItem(EMERALD, GEM, Items.EMERALD)
        MaterialBlocks.addBlock(EMERALD, BLOCK, Blocks.EMERALD_BLOCK)
        MaterialBlocks.addBlock(EMERALD, ORE, Blocks.EMERALD_ORE)

        MaterialItems.addItem(QUARTZ, GEM, Items.QUARTZ)
        MaterialBlocks.addBlock(QUARTZ, BLOCK, Blocks.QUARTZ_BLOCK)
        MaterialBlocks.addBlock(QUARTZ, ORE, Blocks.NETHER_QUARTZ_ORE)

        MaterialItems.addItem(REDSTONE, DUST, Items.REDSTONE)
        MaterialBlocks.addBlock(REDSTONE, BLOCK, Blocks.REDSTONE_BLOCK)
        MaterialBlocks.addBlock(REDSTONE, ORE, Blocks.REDSTONE_ORE)

        MaterialItems.addItem(GLOWSTONE, DUST, Items.GLOWSTONE_DUST)
        MaterialBlocks.addBlock(GLOWSTONE, BLOCK, Blocks.GLOWSTONE)

        MaterialItems.addItem(BRICK, INGOT, Items.BRICK)
        MaterialBlocks.addBlock(BRICK, BLOCK, Blocks.BRICKS)

        MaterialItems.addItem(NETHER_BRICK, INGOT, Items.NETHER_BRICK)
        MaterialBlocks.addBlock(NETHER_BRICK, BLOCK, Blocks.NETHER_BRICKS)

        MaterialItems.addItem(FLINT, GEM, Items.FLINT)

        MaterialItems.addItem(BONE, DUST, Items.BONE_MEAL)
        MaterialBlocks.addBlock(BONE, BLOCK, Blocks.BONE_BLOCK)

        MaterialItems.addItem(BLAZE, DUST, Items.BLAZE_POWDER)

        MaterialFluids.addFluidPair(WATER, FLUID, FluidPairHolder(Fluids.WATER, Fluids.FLOWING_WATER))
        MaterialBlocks.addBlock(WATER, FLUID, Blocks.WATER)
        MaterialItems.addItem(WATER, FLUID, Items.WATER_BUCKET)

        MaterialFluids.addFluidPair(LAVA, FLUID, FluidPairHolder(Fluids.LAVA, Fluids.FLOWING_LAVA))
        MaterialBlocks.addBlock(LAVA, FLUID, Blocks.LAVA)
        MaterialItems.addItem(LAVA, FLUID, Items.LAVA_BUCKET)
    }
}