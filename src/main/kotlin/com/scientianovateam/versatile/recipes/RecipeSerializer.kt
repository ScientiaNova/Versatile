package com.scientianovateam.versatile.recipes

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getObjectOrNull
import com.scientianovateam.versatile.common.extensions.getStringOrNull
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import com.scientianovateam.versatile.common.serialization.IPacketSerializer
import com.scientianovateam.versatile.common.serialization.RECIPE_COMPONENT_HANDLER_SERIALIZERS
import com.scientianovateam.versatile.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.recipes.components.IRecipeHandlerSerializer
import com.scientianovateam.versatile.recipes.lists.RecipeLists
import net.minecraft.network.PacketBuffer

@Suppress("UNCHECKED_CAST")
object RecipeSerializer : IJSONSerializer<Recipe, JsonObject>, IPacketSerializer<Recipe> {
    override fun read(json: JsonObject) = Recipe(
            json.getStringOrNull("list")?.let(RecipeLists::get) ?: error("Missing recipe list for recipe"),
            json.get("name").asString,
            json.getObjectOrNull("components")?.entrySet()?.mapNotNull { (key, element) ->
                RECIPE_COMPONENT_HANDLER_SERIALIZERS[key.toResLocV()]?.read(element)?.resolve(emptyMap())
            } ?: emptyList()
    )

    override fun write(obj: Recipe) = json {
        "list" to obj.recipeList
        "name" to obj.name
        "components" to json {
            obj.componentMap.values.forEach {
                val serializer = it.serializer
                serializer.registryName.toString() to serializer.writeJSON(it)
            }
        }
    }

    override fun read(packet: PacketBuffer) = Recipe(RecipeLists[packet.readResourceLocation()]!!, packet.readString(), List(packet.readVarInt()) {
        RECIPE_COMPONENT_HANDLER_SERIALIZERS[packet.readResourceLocation()]!!.read(packet)
    })

    override fun write(packet: PacketBuffer, obj: Recipe) {
        packet.writeResourceLocation(obj.recipeList.name)
        packet.writeString(obj.name)
        packet.writeVarInt(obj.componentMap.size)
        obj.componentMap.values.forEach { it.serializer.writePacket(packet, it) }
    }

    private fun <T : IRecipeComponentHandler<*>> IRecipeHandlerSerializer<*>.writeJSON(component: T) = (this as IRecipeHandlerSerializer<T>).write(component)

    private fun <T : IRecipeComponentHandler<*>> IRecipeHandlerSerializer<*>.writePacket(packet: PacketBuffer, component: T) = (this as IRecipeHandlerSerializer<T>).write(packet, component)
}