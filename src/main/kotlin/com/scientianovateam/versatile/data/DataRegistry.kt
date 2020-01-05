package com.scientianovateam.versatile.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.data.item.ArmorTierProvider
import com.scientianovateam.versatile.data.item.ToolTierProvider
import com.scientianovateam.versatile.items.tiers.ArmorTier
import com.scientianovateam.versatile.items.tiers.ToolTier
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items.toRStack
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

fun DataGenerator.providers(function: DataGenerator.() -> Unit) = this.function()
fun DataGenerator.toolTiers(function: ToolTierProvider.() -> Unit) = addProvider(ToolTierProvider(this).apply(function))
fun DataGenerator.armorTiers(function: ArmorTierProvider.() -> Unit) = addProvider(ArmorTierProvider(this).apply(function))