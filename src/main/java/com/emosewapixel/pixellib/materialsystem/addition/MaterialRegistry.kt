package com.emosewapixel.pixellib.materialsystem.addition

import com.emosewapixel.pixellib.extensions.toResLoc
import com.emosewapixel.pixellib.fluids.FluidPairHolder
import com.emosewapixel.pixellib.materialsystem.elements.ElementRegistry
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialFluids
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.*
import com.emosewapixel.pixellib.materialsystem.types.ObjectType.Companion.getUnrefinedColor
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
@MaterialRegistryClass(EventPriority.HIGH)
object MaterialRegistry {
    //Material Tags
    const val HAS_ORE = "has_ore"
    const val DISABLE_SIMPLE_PROCESSING = "disable_simple_processing"
    const val BLOCK_FROM_4X4 = "block_from_4x4"
    const val IS_GAS = "is_gas"
    const val HAS_NO_FUEL_VALUE = "has_no_fuel_value"
    const val ROD_TO_3_DUST = "rod_to_3_dust"
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
    val DUST = itemType("dust", { it is DustMaterial }) {
        bucketVolume = 144
    }
    @JvmField
    val GEM = itemType("gem", { it is GemMaterial }) {
        bucketVolume = 144
    }
    @JvmField
    val INGOT = itemType("ingot", { it is IngotMaterial }) {
        bucketVolume = 144
    }
    @JvmField
    val NUGGET = itemType("nugget", { it is IngotMaterial }) {
        bucketVolume = 16
    }
    @JvmField
    val BLOCK = blockType("storage_block", { it is DustMaterial },
            Block.Properties.create(Material.IRON).sound(SoundType.METAL)) {
        buildRegistryName = { ResourceLocation("pixellib:${it.name}_block") }
        bucketVolume = 1296
    }
    @JvmField
    val ORE = blockType("ore", { it is IngotMaterial && it.hasTag(HAS_ORE) },
            Block.Properties.create(Material.ROCK).sound(SoundType.STONE)) {
        typeTags += HAS_NO_FUEL_VALUE
        color = ::getUnrefinedColor
        indexBlackList += 1
        bucketVolume = 144
    }
    @JvmField
    val FLUID = fluidType("fluid", { it is FluidMaterial }) {
        locationBase = "minecraft:block/water"
        fluidColor = { color(it) or ((it as? FluidMaterial)?.alpha?.shl(24) ?: 0xFF000000.toInt()) }
        overlayTexture = ResourceLocation("minecraft", "block/water_overlay")
        buildRegistryName = { ResourceLocation("pixellib:${it.name}") }
        buildTagName = String::toResLoc
    }
    @JvmField
    val MOLTEN_FLUID = fluidType("molten", { it is DustMaterial && it.meltingTemperature > 0 }) {
        buildRegistryName = { ResourceLocation("pixellib:molten_${it.name}") }
        buildTagName = { "forge:molten_$it".toResLoc() }
        emptySound = SoundEvents.ITEM_BUCKET_EMPTY_LAVA
        fillSound = SoundEvents.ITEM_BUCKET_FILL_LAVA
    }

    //Materials
    @JvmField
    val BRICK = ingotMaterial("brick", REGULAR, 0xb55c42, 1) {
        materialTags += BLOCK_FROM_4X4
        typeBlacklist += NUGGET
        compoundType = CompoundType.CHEMICAL
    }
    @JvmField
    val NETHER_BRICK = ingotMaterial("nether_brick", REGULAR, 0x472a30, 1) {
        materialTags += BLOCK_FROM_4X4
        typeBlacklist += NUGGET
        compoundType = CompoundType.CHEMICAL
    }
    @JvmField
    val IRON = ingotMaterial("iron", ROUGH, -1, 1) {
        element = ElementRegistry.IRON
        unrefinedColor = 0x947664
        itemTier = ItemTier.IRON
        armorMaterial = ArmorMaterial.IRON
        meltingTemperature = 1538
        boilingTemperature = 2862
        materialTags += HAS_ORE
    }
    @JvmField
    val GOLD = ingotMaterial("gold", SHINY, 0xfad64a, 2) {
        element = ElementRegistry.GOLD
        itemTier = ItemTier.GOLD
        armorMaterial = ArmorMaterial.GOLD
        meltingTemperature = 1064
        boilingTemperature = 2700
        materialTags += HAS_ORE
    }
    @JvmField
    val COAL = gemMaterial("coal", FUEL, 0x1a1a1a, 0) {
        standardBurnTime = 1600
        element = ElementRegistry.CARBON
        materialTags += HAS_ORE
    }
    @JvmField
    val CHARCOAL = gemMaterial("charcoal", FUEL, 0x443e33, 0) {
        standardBurnTime = 1600
        element = ElementRegistry.CARBON
    }
    @JvmField
    val FLINT = gemMaterial("flint", SHARP, 0x222020, 0) {
        typeBlacklist += BLOCK
    }
    @JvmField
    val LAPIS = gemMaterial("lapis", REGULAR, 0x2351be, 0) {
        materialTags += HAS_ORE
    }
    @JvmField
    val QUARTZ = gemMaterial("quartz", CRYSTAL, 0xe8dfd0, 0) {
        materialTags += listOf(HAS_ORE, BLOCK_FROM_4X4)
    }
    @JvmField
    val DIAMOND = gemMaterial("diamond", PENTAGONAL, 0x34ebe3, 2) {
        element = ElementRegistry.CARBON
        itemTier = ItemTier.DIAMOND
        armorMaterial = ArmorMaterial.DIAMOND
        materialTags += HAS_ORE
    }
    @JvmField
    val EMERALD = gemMaterial("emerald", OCTAGONAL, 0x08ad2c, 2) {
        materialTags += HAS_ORE
    }
    @JvmField
    val WOODEN = dustMaterial("wooden", FINE, 0x87672c, -1) {
        standardBurnTime = 200
        itemTier = ItemTier.WOOD
    }
    @JvmField
    val STONE = dustMaterial("stone", FINE, 0xb1b0ae, 0) {
        itemTier = ItemTier.STONE
    }
    @JvmField
    val BONE = dustMaterial("bone", REGULAR, 0xfcfaed, 0) {
        materialTags += ROD_TO_3_DUST
    }
    @JvmField
    val BLAZE = dustMaterial("blaze", REGULAR, 0xffc20c, 0) {
        materialTags += ROD_TO_3_DUST
    }
    @JvmField
    val REDSTONE = dustMaterial("redstone", REGULAR, 0xfc1a19, 1) {
        materialTags += HAS_ORE
    }
    @JvmField
    val GLOWSTONE = dustMaterial("glowstone", REGULAR, 0xfcbe60, 1) {
        materialTags += BLOCK_FROM_4X4
    }
    @JvmField
    val OBSIDIAN = dustMaterial("obsidian", FINE, 0x3c2a53, 3)
    @JvmField
    val WATER = fluidMaterial("water", FLUID_TT, 0x3e4ac6)
    @JvmField
    val LAVA = fluidMaterial("lava", FLUID_TT, 0xc54c13)

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