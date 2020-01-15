package com.scientianovateam.versatile.items.properties

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import com.scientianovateam.versatile.items.ARMOR_TIERS
import com.scientianovateam.versatile.items.tiers.ArmorTier
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.Food
import net.minecraft.item.Item
import net.minecraft.item.Rarity
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.registries.ForgeRegistries

open class ArmorItemProperties(
        val tier: ArmorTier,
        val slotType: EquipmentSlotType,
        name: String,
        maxStackSize: Int = 1,
        maxDurability: Int = tier.getDurability(slotType),
        containerItemSupplier: () -> Item? = { null },
        rarity: Rarity = Rarity.COMMON,
        canRepair: Boolean = true,
        destroySpeed: Float = 1f,
        food: Food? = null,
        tooltips: List<TranslationTextComponent> = emptyList(),
        translationKey: String = "item.$name",
        glows: Boolean = false,
        isEnchantable: Boolean = true,
        enchantability: Int = tier.enchantability,
        entityLifespan: Int = 6000,
        isBookEnchantable: Boolean = true,
        burnTime: Int = -1
) : ExtendedItemProperties(name, maxStackSize, maxDurability, containerItemSupplier, rarity, canRepair, destroySpeed, food, tooltips, translationKey, glows, isEnchantable, enchantability, entityLifespan, isBookEnchantable, burnTime) {


    object Serializer : IJSONSerializer<ArmorItemProperties, JsonObject> {
        override fun read(json: JsonObject): ArmorItemProperties {
            val name = json.getStringOrNull("name") ?: error("Missing armor item name")
            return ArmorItemProperties(
                    tier = json.getStringOrNull("armor_tier")?.let(ARMOR_TIERS::get) ?: error("Missing armor tier"),
                    slotType = json.getStringOrNull("slot_type")?.let(EquipmentSlotType::fromString)
                            ?: error("Missing slot type"),
                    name = name,
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
                    translationKey = json.getStringOrNull("translation_key") ?: "item.$name",
                    glows = json.getBooleanOrNull("glows") ?: false,
                    isEnchantable = json.getBooleanOrNull("is_enchantable") ?: true,
                    enchantability = json.getIntOrNull("enchantability") ?: 0,
                    entityLifespan = json.getIntOrNull("entity_lifespan") ?: 6000,
                    isBookEnchantable = json.getBooleanOrNull("is_book_enchantable") ?: true,
                    burnTime = json.getIntOrNull("burn_time") ?: -1
            )
        }

        override fun write(obj: ArmorItemProperties) = json {
            "tier" to obj.tier.registryName
            "slot_type" to obj.slotType.getName()
            if (obj.maxStackSize != 1) "max_stack_size" to obj.maxStackSize
            if (obj.maxDurability != obj.tier.getDurability(obj.slotType)) "max_durability" to obj.maxDurability
            obj.containerItem?.let { "container_item" to it }
            if (obj.rarity != Rarity.COMMON) "rarity" to obj.rarity.name.toLowerCase()
            if (!obj.canRepair) "can_repair" to obj.canRepair
            if (obj.destroySpeed != 1f) "destroy_speed" to obj.destroySpeed
            obj.food?.let { "food" to FoodSerializer.write(it) }
            if (obj.tooltips.isNotEmpty()) "tooltips" to obj.tooltips.map { JsonPrimitive(it.key) }
            if (obj.translationKey != "item.${obj.name}") "translation_key" to obj.translationKey
            if (obj.glows) "glows" to obj.glows
            if (!obj.isEnchantable) "is_enchantable" to obj.isEnchantable
            if (obj.enchantability != obj.enchantability) "enchantability" to obj.enchantability
            if (obj.entityLifespan != 6000) "entity_lifespan" to obj.entityLifespan
            if (!obj.isBookEnchantable) "is_book_enchantable" to obj.isBookEnchantable
            if (obj.burnTime > 0) "burn_time" to obj.burnTime
        }
    }
}