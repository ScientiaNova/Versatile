package com.scientianovateam.versatile.items.properties

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import net.minecraft.item.Food
import net.minecraft.item.Item
import net.minecraft.item.Rarity
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.registries.ForgeRegistries

open class ExtendedItemProperties(
        val maxStackSize: Int = 64,
        val maxDurability: Int = 0,
        containerItemSupplier: () -> Item? = { null },
        val rarity: Rarity = Rarity.COMMON,
        val canRepair: Boolean = true,
        val destroySpeed: Float = 1f,
        val food: Food? = null,
        val tooltips: List<TranslationTextComponent> = emptyList(),
        val translationKey: String? = null,
        val glows: Boolean = false,
        val isEnchantable: Boolean = maxStackSize == 1 && maxDurability > 0,
        val enchantability: Int = 0,
        val entityLifespan: Int = 6000,
        val isBookEnchantable: Boolean = true,
        val burnTime: Int = -1
) : Item.Properties() {
    val containerItem by lazy(containerItemSupplier)

    init {
        if (food != null) super.food(food)
        super.maxStackSize(maxStackSize)
        super.maxDamage(maxDurability)
        super.group(Versatile.MAIN)
        super.rarity(rarity)
        if (canRepair) super.setNoRepair()
    }

    object Serializer : IJSONSerializer<ExtendedItemProperties, JsonObject> {
        override fun read(json: JsonObject) = ExtendedItemProperties(
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

        override fun write(obj: ExtendedItemProperties) = json {
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