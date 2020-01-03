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
                maxStackSize = json.getPrimitiveOrNull("max_stack_size")?.asInt ?: 64,
                maxDurability = json.getPrimitiveOrNull("max_durability")?.asInt ?: -1,
                containerItemSupplier = {
                    json.getStringOrNull("container_item")?.let { ForgeRegistries.ITEMS.getValue(it.toResLoc()) }
                },
                rarity = json.getStringOrNull("rarity")?.let { Rarity.valueOf(it.toUpperCase()) } ?: Rarity.COMMON,
                canRepair = json.getPrimitiveOrNull("can_repair")?.asBoolean ?: true,
                destroySpeed = json.getPrimitiveOrNull("destroy_speed")?.asFloat ?: 1f,
                food = json.getObjectOrNull("food")?.let { FoodSerializer.read(it) },
                tooltips = json.getArrayOrNull("tooltips")?.map { TranslationTextComponent(it.asString) }
                        ?: emptyList(),
                translationKey = json.getStringOrNull("translation_key"),
                glows = json.getPrimitiveOrNull("glows")?.asBoolean ?: false,
                isEnchantable = json.getPrimitiveOrNull("is_enchantable")?.asBoolean ?: true,
                enchantability = json.getPrimitiveOrNull("enchantability")?.asInt ?: 0,
                entityLifespan = json.getPrimitiveOrNull("entity_lifespan")?.asInt ?: 6000,
                isBookEnchantable = json.getPrimitiveOrNull("is_book_enchantable")?.asBoolean ?: true,
                burnTime = json.getAsJsonPrimitive("burn_time")?.asInt ?: -1
        )

        override fun write(obj: ToolTierBasedProperties) = json {
            "tool_tier" to obj.tier.registryName
            "max_stack_size" to obj.maxStackSize
            "max_durability" to obj.maxDurability
            obj.containerItem?.registryName?.toString()?.let { "container_item" to it }
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