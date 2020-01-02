package com.scientianovateam.versatile.items.tiers

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getObjectOrNull
import com.scientianovateam.versatile.common.extensions.getPrimitiveOrNull
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import com.scientianovateam.versatile.recipes.RECIPE_ITEM_STACK_SERIALIZERS
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items.RecipeItemStack
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.serialize
import net.minecraft.item.ItemTier

object ToolTierSerializer : IJSONSerializer<ToolTier, JsonObject> {
    override fun read(json: JsonObject) = ToolTier(
            json.getPrimitiveOrNull("max_uses")?.asInt ?: ItemTier.WOOD.maxUses,
            json.getPrimitiveOrNull("efficiency")?.asFloat ?: ItemTier.WOOD.efficiency,
            json.getPrimitiveOrNull("attack_damage")?.asFloat ?: ItemTier.WOOD.attackDamage,
            json.getPrimitiveOrNull("harvest_level")?.asInt ?: ItemTier.WOOD.harvestLevel,
            json.getPrimitiveOrNull("enchantability")?.asInt ?: ItemTier.WOOD.enchantability
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