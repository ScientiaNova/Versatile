package com.scientianovateam.versatile.items.properties

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import com.scientianovateam.versatile.items.TOOL_TIERS
import com.scientianovateam.versatile.items.tiers.ToolTier
import net.minecraft.item.Food
import net.minecraft.item.Item
import net.minecraft.item.Rarity
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.registries.ForgeRegistries

open class ToolTierBasedProperties(
        val tier: ToolTier,
        maxStackSize: Int = 1,
        maxDurability: Int = tier.maxUses,
        containerItemSupplier: () -> Item? = { null },
        rarity: Rarity = Rarity.COMMON,
        canRepair: Boolean = true,
        destroySpeed: Float = tier.efficiency,
        food: Food? = null,
        tooltips: List<TranslationTextComponent> = emptyList(),
        translationKey: String? = null,
        glows: Boolean = false,
        isEnchantable: Boolean = true,
        enchantability: Int = tier.enchantability,
        entityLifespan: Int = 6000,
        isBookEnchantable: Boolean = true,
        burnTime: Int = -1
) : ExtendedItemProperties(maxStackSize, maxDurability, containerItemSupplier, rarity, canRepair, destroySpeed, food, tooltips, translationKey, glows, isEnchantable, enchantability, entityLifespan, isBookEnchantable, burnTime) {

    object Serializer : IJSONSerializer<ToolTierBasedProperties, JsonObject> {
        override fun read(json: JsonObject) = ToolTierBasedProperties(
                tier = json.getStringOrNull("tool_tier")?.let(TOOL_TIERS::get) ?: error("Missing tool tier"),
                maxStackSize = json.getIntOrNull("max_stack_size") ?: 64,
                maxDurability = json.getIntOrNull("max_durability") ?: -1,
                containerItemSupplier = {
                    json.getStringOrNull("container_item")?.let { ForgeRegistries.ITEMS.getValue(it.toResLoc()) }
                },
                rarity = json.getStringOrNull("rarity")?.let { Rarity.valueOf(it.toUpperCase()) } ?: Rarity.COMMON,
                canRepair = json.getBooleanOrNull("can_repair") ?: true,
                destroySpeed = json.getFloatOrNull("destroy_speed") ?: 1f,
                food = json.getObjectOrNull("food")?.let { FoodSerializer.read(it) },
                tooltips = json.getArrayOrNull("tooltips")?.map { TranslationTextComponent(it.asString) }
                        ?: emptyList(),
                translationKey = json.getStringOrNull("translation_key"),
                glows = json.getBooleanOrNull("glows") ?: false,
                isEnchantable = json.getBooleanOrNull("is_enchantable") ?: true,
                enchantability = json.getIntOrNull("enchantability") ?: 0,
                entityLifespan = json.getIntOrNull("entity_lifespan") ?: 6000,
                isBookEnchantable = json.getBooleanOrNull("is_book_enchantable") ?: true,
                burnTime = json.getIntOrNull("burn_time") ?: -1
        )

        override fun write(obj: ToolTierBasedProperties) = json {
            "tool_tier" to obj.tier.registryName
            "max_stack_size" to obj.maxStackSize
            "max_durability" to obj.maxDurability
            obj.containerItem?.let { "container_item" to it }
            "rarity" to obj.rarity.name.toLowerCase()
            "can_repair" to obj.canRepair
            "destroy_speed" to obj.destroySpeed
            obj.food?.let { "food" to FoodSerializer.write(it) }
            "tooltips" to obj.tooltips.map { JsonPrimitive(it.key) }
            obj.translationKey?.let { "translation_key" to it }
            "glows" to obj.glows
            "is_enchantable" to obj.isEnchantable
            "enchantability" to obj.enchantability
            "entity_lifespan" to obj.entityLifespan
            "is_book_enchantable" to obj.isBookEnchantable
            "burn_time" to obj.burnTime
        }
    }
}