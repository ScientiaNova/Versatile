package com.scientianovateam.versatile.items

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.registry.NamespacelessRegistry
import com.scientianovateam.versatile.common.registry.Registry
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.items.serializable.ISerializableItem
import com.scientianovateam.versatile.items.tiers.ArmorTier
import com.scientianovateam.versatile.items.tiers.ToolTier

val TOOL_TIERS = NamespacelessRegistry<ToolTier>()
val ARMOR_TIERS = NamespacelessRegistry<ArmorTier>()
val SERIALIZED_ITEMS = Registry<ISerializableItem>()
val ITEM_SERIALIZERS = Registry<IRegisterableJSONSerializer<out ISerializableItem, JsonObject>>()