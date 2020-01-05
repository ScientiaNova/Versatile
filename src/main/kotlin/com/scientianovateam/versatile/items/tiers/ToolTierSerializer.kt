package com.scientianovateam.versatile.items.tiers

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import com.scientianovateam.versatile.recipes.RECIPE_ITEM_STACK_SERIALIZERS
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items.RecipeItemStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.serialize
import net.minecraft.item.ItemTier

object ToolTierSerializer : IJSONSerializer<ToolTier, JsonObject> {
    override fun read(json: JsonObject) = ToolTier(
            registryName = json.getStringOrNull("name") ?: error("Missing registry name for item tier"),
            maxUses = json.getIntOrNull("max_uses") ?: ItemTier.WOOD.maxUses,
            efficiency = json.getFloatOrNull("efficiency") ?: ItemTier.WOOD.efficiency,
            attackDamage = json.getFloatOrNull("attack_damage") ?: ItemTier.WOOD.attackDamage,
            harvestLevel = json.getIntOrNull("harvest_level") ?: ItemTier.WOOD.harvestLevel,
            enchantability = json.getIntOrNull("enchantability") ?: ItemTier.WOOD.enchantability
    ) {
        json.getStringOrNull("type")?.let {
            RECIPE_ITEM_STACK_SERIALIZERS[it.toResLocV()]?.read(json)?.resolve()
        } ?: RecipeItemStack.EMPTY
    }

    override fun write(obj: ToolTier) = json {
        "max_uses" to obj.maxUses
        "efficiency" to obj.efficiency
        "attack_damage" to obj.attackDamage
        "harvest_level" to obj.harvestLevel
        "enchantability" to obj.enchantability
        "repair_ingredient" {
            val repairStack = obj.repairRecipeStack
            "type" to repairStack.serializer.registryName
            repairStack.serialize().extract()
        }
    }
}