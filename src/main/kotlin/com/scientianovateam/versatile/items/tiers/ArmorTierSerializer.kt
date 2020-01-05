package com.scientianovateam.versatile.items.tiers

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import com.scientianovateam.versatile.recipes.RECIPE_ITEM_STACK_SERIALIZERS
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items.RecipeItemStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.serialize
import net.minecraft.item.ArmorMaterial
import net.minecraftforge.registries.ForgeRegistries

object ArmorTierSerializer : IJSONSerializer<ArmorTier, JsonObject> {
    override fun read(json: JsonObject) = ArmorTier(
            registryName = json.getStringOrNull("name") ?: error("Missing name for armor tier"),
            durability = json.getArrayOrNull("durability")?.map(JsonElement::getAsInt)?.toIntArray()
                    ?: intArrayOf(0, 0, 0, 0),
            damageReduction = json.getArrayOrNull("damage_reduction")?.map(JsonElement::getAsInt)?.toIntArray()
                    ?: intArrayOf(0, 0, 0, 0),
            enchantability = json.getIntOrNull("enchantability") ?: ArmorMaterial.LEATHER.enchantability,
            soundSupplier = {
                json.getStringOrNull("sound")?.let { ForgeRegistries.SOUND_EVENTS.getValue(it.toResLoc()) }
                        ?: ArmorMaterial.IRON.soundEvent
            },
            toughness = json.getFloatOrNull("toughness") ?: ArmorMaterial.LEATHER.toughness
    ) {
        json.getObjectOrNull("repair_ingredient")?.let { ingredientJson ->
            json.getStringOrNull("type")?.let {
                RECIPE_ITEM_STACK_SERIALIZERS[it.toResLocV()]?.read(ingredientJson)?.resolve()
            }
        } ?: RecipeItemStack.EMPTY
    }

    override fun write(obj: ArmorTier) = json {
        "durability" to obj.durability.toJson()
        "damage_reduction" to obj.damageReduction.toJson()
        "enchantability" to obj.enchantability
        "sound" to obj.soundEvent
        "toughness" to obj.toughness
        "repair_ingredient" {
            val repairStack = obj.repairRecipeStack
            "type" to repairStack.serializer.registryName
            repairStack.serialize().extract()
        }
    }
}