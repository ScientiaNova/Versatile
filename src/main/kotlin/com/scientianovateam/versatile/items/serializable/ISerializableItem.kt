package com.scientianovateam.versatile.items.serializable

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import net.minecraft.item.Item
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.common.extensions.IForgeItem
import net.minecraftforge.registries.IForgeRegistryEntry

interface ISerializableItem : IForgeItem, IForgeRegistryEntry<Item> {
    fun setLocalization(function: () -> ITextComponent)
    val serializer: IJSONSerializer<out ISerializableItem, JsonObject>
}

@Suppress("UNCHECKED_CAST")
fun <T : ISerializableItem> T.serialize() = (serializer as IJSONSerializer<T, JsonObject>).write(this)