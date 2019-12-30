package com.scientianovateam.versatile.recipes.components.ingredients.items.input

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

class ItemInputsHandler(override val value: List<ChancedRecipeStack<ItemStack>>) : IRecipeComponentHandler<List<ChancedRecipeStack<ItemStack>>> {
    override val pairedComponentType = ItemInputsComponent::class.java
    override val serializer: IRecipeHandlerSerializer<out IRecipeComponentHandler<List<ChancedRecipeStack<ItemStack>>>> = Serializer

    object Serializer : IRecipeHandlerSerializer<ItemInputsHandler>("item_inputs".toResLocV()) {
        override fun read(json: JsonElement) = when (json) {
            is JsonObject -> ItemInputsHandlerIntermediate(listOf(ChancedRecipeItemStackSerializer.read(json)))
            is JsonArray -> ItemInputsHandlerIntermediate(json.map { ChancedRecipeItemStackSerializer.read(it.asJsonObject) })
            else -> error("Invalid item inputs for recipe")
        }

        override fun write(obj: ItemInputsHandler) = obj.value.map { ChancedRecipeItemStackSerializer.write(it) }.toJson()

        override fun read(packet: PacketBuffer) = ItemInputsHandler(List(packet.readVarInt()) { ChancedRecipeItemStackSerializer.read(packet) })

        override fun write(packet: PacketBuffer, obj: ItemInputsHandler) {
            packet.writeVarInt(obj.value.size)
            obj.value.forEach { ChancedRecipeItemStackSerializer.write(packet, it) }
        }
    }
}