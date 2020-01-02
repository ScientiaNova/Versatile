package com.scientianovateam.versatile.items

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.registry.Registry
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.items.tiers.ArmorTier
import com.scientianovateam.versatile.items.tiers.ToolTier
import net.minecraft.item.Item

val TOOL_TIERS = Registry<ToolTier>()
val ARMOR_TIER = Registry<ArmorTier>()
val SERIALIZED_ITEMS = Registry<Item>()
val ITEM_SERIALIZERS = Registry<IRegisterableJSONSerializer<out Item, JsonObject>>()