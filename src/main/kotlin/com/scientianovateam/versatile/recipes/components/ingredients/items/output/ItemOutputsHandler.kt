package com.scientianovateam.versatile.recipes.components.ingredients.items.output

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.toJson
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.recipes.components.IRecipeHandlerSerializer
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.ChancedRecipeItemStackSerializer
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer

class ItemOutputsHandler(override val value: List<ChancedRecipeStack<ItemStack>>) : IRecipeComponentHandler<List<ChancedRecipeStack<ItemStack>>> {
    override val pairedComponentType = ItemOutputsComponent::class.java
    override val serializer = Serializer

    object Serializer : IRecipeHandlerSerializer<ItemOutputsHandler>("item_outputs".toResLocV()) {
        override fun read(json: JsonElement) = when (json) {
            is JsonObject -> ItemOutputsHandlerIntermediate(listOf(ChancedRecipeItemStackSerializer.read(json)))
            is JsonArray -> ItemOutputsHandlerIntermediate(json.map { ChancedRecipeItemStackSerializer.read(it.asJsonObject) })
            else -> error("Invalid item outputs for recipe")
        }

        override fun write(obj: ItemOutputsHandler) = obj.value.map { ChancedRecipeItemStackSerializer.write(it) }.toJson()

        override fun read(packet: PacketBuffer) = ItemOutputsHandler(List(packet.readVarInt()) { ChancedRecipeItemStackSerializer.read(packet) })

        override fun write(packet: PacketBuffer, obj: ItemOutputsHandler) {
            packet.writeVarInt(obj.value.size)
            obj.value.forEach { ChancedRecipeItemStackSerializer.write(packet, it) }
        }
    }
}