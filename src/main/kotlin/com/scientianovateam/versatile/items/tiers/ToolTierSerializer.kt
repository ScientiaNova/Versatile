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
            json.getStringOrNull("name") ?: error("Missing registry name for item tier"),
            json.getIntOrNull("max_uses") ?: ItemTier.WOOD.maxUses,
            json.getFloatOrNull("efficiency") ?: ItemTier.WOOD.efficiency,
            json.getFloatOrNull("attack_damage") ?: ItemTier.WOOD.attackDamage,
            json.getIntOrNull("harvest_level") ?: ItemTier.WOOD.harvestLevel,
            json.getIntOrNull("enchantability") ?: ItemTier.WOOD.enchantability
    ) {
        json.getObjectOrNull("repair_ingredient")?.entrySet()?.firstOrNull()?.let { (serializer, value) ->
            (value as? JsonObject)?.let { RECIPE_ITEM_STACK_SERIALIZERS[serializer.toResLocV()]?.read(it)?.resolve(emptyMap()) }
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
            repairStack.serializer.registryName.toString() to repairStack.serialize()
        }
    }
}