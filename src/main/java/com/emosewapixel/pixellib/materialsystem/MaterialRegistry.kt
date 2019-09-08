package com.emosewapixel.pixellib.materialsystem

import com.emosewapixel.pixellib.materialsystem.element.Elements
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.CompoundType
import com.emosewapixel.pixellib.materialsystem.materials.DustMaterial
import com.emosewapixel.pixellib.materialsystem.materials.GemMaterial
import com.emosewapixel.pixellib.materialsystem.materials.IngotMaterial
import com.emosewapixel.pixellib.materialsystem.types.TextureType
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.ItemTier
import net.minecraft.item.Items
import java.util.function.Predicate

//This class is used for registering the vanilla material and object type tags, materials and object types
object MaterialRegistry {
    //Material Tags
    @JvmStatic
    val HAS_ORE = "has_ore"
    @JvmStatic
    val DISABLE_SIMPLE_PROCESSING = "disable_simple_processing"
    @JvmStatic
    val BLOCK_FROM_4X4 = "block_from_4x4"
    @JvmStatic
    val IS_GAS = "is_gas"
    @JvmStatic
    val HAS_NO_FUEL_VALUE = "has_no_fuel_value"
    @JvmStatic
    val ROD_TO_3_DUST = "rod_to_3_dust"
    @JvmStatic
    val SINGLE_TEXTURE_TYPE = "1_texture_type"
    @JvmStatic
    val USES_UNREFINED_COLOR = "uses_unrefined_color"

    //Texture Types
    @JvmStatic
    val ROUGH = TextureType("rough")
    @JvmStatic
    val REGULAR = TextureType("regular")
    @JvmStatic
    val SHINY = TextureType("shiny")
    @JvmStatic
    val FUEL = TextureType("fuel")
    @JvmStatic
    val PENTAGONAL = TextureType("pentagonal")
    @JvmStatic
    val OCTAGONAL = TextureType("octagonal")
    @JvmStatic
    val CRYSTAL = TextureType("crystal")
    @JvmStatic
    val SHARP = TextureType("sharp")
    @JvmStatic
    val FINE = TextureType("fine")
    @JvmStatic
    val TRANSPARENT_FLUID = TextureType("transparent")
    @JvmStatic
    val OPAQUE_FLUID = TextureType("opaque")

    //Object Types
    @JvmStatic
    val DUST = itemType("dust", Predicate { it is DustMaterial }) {
        bucketVolume = 144
    }
    @JvmStatic
    val GEM = itemType("gem", Predicate { it is GemMaterial }) {
        bucketVolume = 144
    }
    @JvmStatic
    val INGOT = itemType("ingot", Predicate { it is IngotMaterial }) {
        bucketVolume = 144
    }
    @JvmStatic
    val NUGGET = itemType("nugget", Predicate { it is IngotMaterial }) {
        bucketVolume = 16
    }
    @JvmStatic
    val BLOCK = blockType("storage_block", Predicate { it is DustMaterial },
            Block.Properties.create(Material.IRON).sound(SoundType.METAL)) {
        bucketVolume = 1296
    }
    @JvmStatic
    val ORE = blockType("ore", Predicate { it is IngotMaterial && HAS_ORE in it.materialTags },
            Block.Properties.create(Material.ROCK).sound(SoundType.STONE)) {
        typeTags += listOf(USES_UNREFINED_COLOR, HAS_NO_FUEL_VALUE)
        bucketVolume = 144
    }

    //Materials
    @JvmStatic
    val BRICK = ingotMaterial("brick", REGULAR, 0xb55c42, 1) {
        materialTags += BLOCK_FROM_4X4
        typeBlacklist += NUGGET
        compoundType = CompoundType.CHEMICAL
    }
    @JvmStatic
    val NETHER_BRICK = ingotMaterial("nether_brick", REGULAR, 0x472a30, 1) {
        materialTags += BLOCK_FROM_4X4
        typeBlacklist += NUGGET
        compoundType = CompoundType.CHEMICAL
    }
    @JvmStatic
    val IRON = ingotMaterial("iron", ROUGH, -1, 1) {
        element = Elements.IRON
        unrefinedColor = 0x947664
        itemTier = ItemTier.IRON
        armorMaterial = ArmorMaterial.IRON
        materialTags += HAS_ORE
    }
    @JvmStatic
    val GOLD = ingotMaterial("gold", SHINY, 0xfad64a, 2) {
        element = Elements.GOLD
        itemTier = ItemTier.GOLD
        armorMaterial = ArmorMaterial.GOLD
        materialTags += HAS_ORE
    }
    @JvmStatic
    val COAL = gemMaterial("coal", FUEL, 0x1a1a1a, 0) {
        standardBurnTime = 1666
        element = Elements.CARBON
        materialTags += HAS_ORE
    }
    @JvmStatic
    val CHARCOAL = gemMaterial("charcoal", FUEL, 0x443e33, 0) {
        standardBurnTime = 1666
        element = Elements.CARBON
    }
    @JvmStatic
    val FLINT = gemMaterial("flint", SHARP, 0x222020, 0) {
        typeBlacklist += BLOCK
    }
    @JvmStatic
    val LAPIS = gemMaterial("lapis", REGULAR, 0x2351be, 0) {
        materialTags += HAS_ORE
    }
    @JvmStatic
    val QUARTZ = gemMaterial("quartz", CRYSTAL, 0xe8dfd0, 0) {
        materialTags += listOf(HAS_ORE, BLOCK_FROM_4X4)
    }
    @JvmStatic
    val DIAMOND = gemMaterial("diamond", PENTAGONAL, 0x34ebe3, 2) {
        element = Elements.CARBON
        itemTier = ItemTier.DIAMOND
        armorMaterial = ArmorMaterial.DIAMOND
        materialTags += HAS_ORE
    }
    @JvmStatic
    val EMERALD = gemMaterial("emerald", OCTAGONAL, 0x08ad2c, 2) {
        materialTags += HAS_ORE
    }
    @JvmStatic
    val WOODEN = dustMaterial("wooden", FINE, 0x87672c, -1) {
        standardBurnTime = 200
        itemTier = ItemTier.WOOD
    }
    @JvmStatic
    val STONE = dustMaterial("stone", FINE, 0xb1b0ae, 0) {
        itemTier = ItemTier.STONE
    }
    @JvmStatic
    val BONE = dustMaterial("bone", REGULAR, 0xfcfaed, 0) {
        materialTags += ROD_TO_3_DUST
    }
    @JvmStatic
    val BLAZE = dustMaterial("bone", REGULAR, 0xffc20c, 0) {
        materialTags += ROD_TO_3_DUST
    }
    @JvmStatic
    val REDSTONE = dustMaterial("redstone", REGULAR, 0xfc1a19, 1) {
        materialTags += HAS_ORE
    }
    @JvmStatic
    val GLOWSTONE = dustMaterial("glowstone", REGULAR, 0xfcbe60, 1) {
        materialTags += BLOCK_FROM_4X4
    }
    @JvmStatic
    val OBSIDIAN = dustMaterial("obsidian", FINE, 0x3c2a53, 3) {}
    @JvmStatic
    val WATER = fluidMaterial("water", TRANSPARENT_FLUID, 0x3e4ac6) {}
    @JvmStatic
    val LAVA = fluidMaterial("lava", OPAQUE_FLUID, 0xc54c13) {}

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
    }
}