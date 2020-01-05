package com.scientianovateam.versatile.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.blocks.properties.BlockMaterialWrapper
import com.scientianovateam.versatile.blocks.properties.SoundTypeV
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.data.block.BlockMaterialProvider
import com.scientianovateam.versatile.data.block.BlockProvider
import com.scientianovateam.versatile.data.block.SoundTypeProvider
import com.scientianovateam.versatile.data.item.ArmorTierProvider
import com.scientianovateam.versatile.data.item.ItemProvider
import com.scientianovateam.versatile.data.item.ToolTierProvider
import com.scientianovateam.versatile.data.recipe.RecipeListProvider
import com.scientianovateam.versatile.items.tiers.ArmorTier
import com.scientianovateam.versatile.items.tiers.ToolTier
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items.toRStack
import net.minecraft.block.material.MaterialColor
import net.minecraft.block.material.PushReaction
import net.minecraft.data.DataGenerator
import net.minecraft.item.Items
import net.minecraft.tags.ItemTags
import net.minecraft.util.SoundEvents
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent

@Mod.EventBusSubscriber(modid = Versatile.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object DataRegistry {
    @SubscribeEvent
    fun gatherData(e: GatherDataEvent) = e.generator.providers {
        soundTypes {
            +SoundTypeV(
                    registryName = "wood".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_WOOD_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_WOOD_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_WOOD_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_WOOD_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_WOOD_FALL }
            )
            +SoundTypeV(
                    registryName = "ground".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_GRAVEL_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_GRAVEL_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_GRAVEL_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_GRAVEL_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_GRAVEL_FALL }
            )
            +SoundTypeV(
                    registryName = "plant".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_GRASS_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_GRASS_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_GRASS_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_GRASS_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_GRASS_FALL }
            )
            +SoundTypeV(
                    registryName = "stone".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_STONE_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_STONE_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_STONE_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_STONE_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_STONE_FALL }
            )
            +SoundTypeV(
                    registryName = "metal".toResLoc(),
                    pitch = 1.5f,
                    breakSoundSupplier = { SoundEvents.BLOCK_METAL_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_METAL_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_METAL_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_METAL_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_METAL_FALL }
            )
            +SoundTypeV(
                    registryName = "glass".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_GLASS_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_GLASS_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_GLASS_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_GLASS_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_GLASS_FALL }
            )
            +SoundTypeV(
                    registryName = "cloth".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_WOOL_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_WOOL_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_WOOL_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_WOOL_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_WOOL_FALL }
            )
            +SoundTypeV(
                    registryName = "sand".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_SAND_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_SAND_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_SAND_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_SAND_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_SAND_FALL }
            )
            +SoundTypeV(
                    registryName = "snow".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_SNOW_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_SNOW_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_SNOW_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_SNOW_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_SNOW_FALL }
            )
            +SoundTypeV(
                    registryName = "ladder".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_LADDER_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_LADDER_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_LADDER_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_LADDER_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_LADDER_FALL }
            )
            +SoundTypeV(
                    registryName = "anvil".toResLoc(),
                    volume = 0.3f,
                    breakSoundSupplier = { SoundEvents.BLOCK_ANVIL_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_ANVIL_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_ANVIL_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_ANVIL_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_ANVIL_FALL }
            )
            +SoundTypeV(
                    registryName = "slime".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_SLIME_BLOCK_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_SLIME_BLOCK_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_SLIME_BLOCK_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_SLIME_BLOCK_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_SLIME_BLOCK_FALL }
            )
            +SoundTypeV(
                    registryName = "wet_grass".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_WET_GRASS_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_WET_GRASS_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_WET_GRASS_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_WET_GRASS_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_WET_GRASS_FALL }
            )
            +SoundTypeV(
                    registryName = "coral".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_CORAL_BLOCK_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_CORAL_BLOCK_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_CORAL_BLOCK_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_CORAL_BLOCK_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_CORAL_BLOCK_FALL }
            )
            +SoundTypeV(
                    registryName = "bamboo".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_BAMBOO_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_BAMBOO_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_BAMBOO_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_BAMBOO_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_BAMBOO_FALL }
            )
            +SoundTypeV(
                    registryName = "bamboo_sapling".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_BAMBOO_SAPLING_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_BAMBOO_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_BAMBOO_SAPLING_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_BAMBOO_SAPLING_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_BAMBOO_FALL }
            )
            +SoundTypeV(
                    registryName = "scaffolding".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_SCAFFOLDING_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_SCAFFOLDING_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_SCAFFOLDING_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_SCAFFOLDING_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_SCAFFOLDING_FALL }
            )
            +SoundTypeV(
                    registryName = "sweet_berry_bush".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_SWEET_BERRY_BUSH_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_GRASS_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_GRASS_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_GRASS_FALL }
            )
            +SoundTypeV(
                    registryName = "crop".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_CROP_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_GRASS_STEP },
                    placeSoundSupplier = { SoundEvents.ITEM_CROP_PLANT },
                    hitSoundSupplier = { SoundEvents.BLOCK_GRASS_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_GRASS_FALL }
            )
            +SoundTypeV(
                    registryName = "stem".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_WOOD_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_WOOD_STEP },
                    placeSoundSupplier = { SoundEvents.ITEM_CROP_PLANT },
                    hitSoundSupplier = { SoundEvents.BLOCK_WOOD_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_WOOD_FALL }
            )
            +SoundTypeV(
                    registryName = "nether_wart".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_NETHER_WART_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_STONE_STEP },
                    placeSoundSupplier = { SoundEvents.ITEM_NETHER_WART_PLANT },
                    hitSoundSupplier = { SoundEvents.BLOCK_STONE_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_STONE_FALL }
            )
            +SoundTypeV(
                    registryName = "lantern".toResLoc(),
                    breakSoundSupplier = { SoundEvents.BLOCK_LANTERN_BREAK },
                    stepSoundSupplier = { SoundEvents.BLOCK_LANTERN_STEP },
                    placeSoundSupplier = { SoundEvents.BLOCK_LANTERN_PLACE },
                    hitSoundSupplier = { SoundEvents.BLOCK_LANTERN_HIT },
                    fallSoundSupplier = { SoundEvents.BLOCK_LANTERN_FALL }
            )
        }
        blockMaterials {
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
        toolTiers {
            +ToolTier(
                    registryName = "wood",
                    maxUses = 59,
                    efficiency = 2f,
                    attackDamage = 0f,
                    harvestLevel = 0,
                    enchantability = 15,
                    repairRecipeStackSupplier = { ItemTags.PLANKS.toRStack() }
            )
            +ToolTier(
                    registryName = "stone",
                    maxUses = 131,
                    efficiency = 4f,
                    attackDamage = 1f,
                    harvestLevel = 1,
                    enchantability = 5,
                    repairRecipeStackSupplier = { Items.COBBLESTONE.toRStack() }
            )
            +ToolTier(
                    registryName = "iron",
                    maxUses = 250,
                    efficiency = 6f,
                    attackDamage = 2f,
                    harvestLevel = 2,
                    enchantability = 14,
                    repairRecipeStackSupplier = { Items.IRON_INGOT.toRStack() }
            )
            +ToolTier(
                    registryName = "diamond",
                    maxUses = 1561,
                    efficiency = 8f,
                    attackDamage = 3f,
                    harvestLevel = 3,
                    enchantability = 10,
                    repairRecipeStackSupplier = { Items.DIAMOND.toRStack() }
            )
            +ToolTier(
                    registryName = "gold",
                    maxUses = 32,
                    efficiency = 12f,
                    attackDamage = 00f,
                    harvestLevel = 0,
                    enchantability = 22,
                    repairRecipeStackSupplier = { Items.GOLD_INGOT.toRStack() }
            )
        }
        armorTiers {
            +ArmorTier(
                    registryName = "leather",
                    durability = intArrayOf(65, 75, 80, 55),
                    damageReduction = intArrayOf(1, 2, 3, 1),
                    enchantability = 15,
                    soundSupplier = { SoundEvents.ITEM_ARMOR_EQUIP_LEATHER },
                    toughness = 0f,
                    repairRecipeStackSupplier = { Items.LEATHER.toRStack() }
            )
            +ArmorTier(
                    registryName = "chainmail",
                    durability = intArrayOf(195, 225, 240, 165),
                    damageReduction = intArrayOf(1, 4, 5, 2),
                    enchantability = 12,
                    soundSupplier = { SoundEvents.ITEM_ARMOR_EQUIP_CHAIN },
                    toughness = 0f,
                    repairRecipeStackSupplier = { Items.IRON_INGOT.toRStack() }
            )
            +ArmorTier(
                    registryName = "iron",
                    durability = intArrayOf(195, 225, 240, 165),
                    damageReduction = intArrayOf(2, 5, 6, 2),
                    enchantability = 9,
                    soundSupplier = { SoundEvents.ITEM_ARMOR_EQUIP_IRON },
                    toughness = 0f,
                    repairRecipeStackSupplier = { Items.IRON_INGOT.toRStack() }
            )
            +ArmorTier(
                    registryName = "gold",
                    durability = intArrayOf(91, 105, 112, 77),
                    damageReduction = intArrayOf(1, 3, 5, 2),
                    enchantability = 25,
                    soundSupplier = { SoundEvents.ITEM_ARMOR_EQUIP_GOLD },
                    toughness = 0f,
                    repairRecipeStackSupplier = { Items.GOLD_INGOT.toRStack() }
            )
            +ArmorTier(
                    registryName = "diamond",
                    durability = intArrayOf(429, 495, 528, 363),
                    damageReduction = intArrayOf(3, 6, 8, 3),
                    enchantability = 10,
                    soundSupplier = { SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND },
                    toughness = 2f,
                    repairRecipeStackSupplier = { Items.DIAMOND.toRStack() }
            )
            +ArmorTier(
                    registryName = "turtle",
                    durability = intArrayOf(325, 375, 400, 275),
                    damageReduction = intArrayOf(2, 5, 6, 2),
                    enchantability = 9,
                    soundSupplier = { SoundEvents.ITEM_ARMOR_EQUIP_TURTLE },
                    toughness = 0f,
                    repairRecipeStackSupplier = { Items.SCUTE.toRStack() }
            )
        }
    }
}

val GSON: Gson = GsonBuilder().setPrettyPrinting().create()

fun DataGenerator.soundTypes(function: SoundTypeProvider.() -> Unit) = addProvider(SoundTypeProvider(this).apply(function))
fun DataGenerator.blockMaterials(function: BlockMaterialProvider.() -> Unit) = addProvider(BlockMaterialProvider(this).apply(function))
fun DataGenerator.blocks(function: BlockProvider.() -> Unit) = addProvider(BlockProvider(this).apply(function))
fun DataGenerator.providers(function: DataGenerator.() -> Unit) = this.function()
fun DataGenerator.toolTiers(function: ToolTierProvider.() -> Unit) = addProvider(ToolTierProvider(this).apply(function))
fun DataGenerator.armorTiers(function: ArmorTierProvider.() -> Unit) = addProvider(ArmorTierProvider(this).apply(function))
fun DataGenerator.items(function: ItemProvider.() -> Unit) = addProvider(ItemProvider(this).apply(function))
fun DataGenerator.recipeLists(function: RecipeListProvider.() -> Unit) = addProvider(RecipeListProvider(this).apply(function))