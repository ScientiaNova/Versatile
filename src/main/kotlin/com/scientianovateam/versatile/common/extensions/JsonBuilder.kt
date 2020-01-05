package com.scientianovateam.versatile.common.extensions

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.IForgeRegistryEntry

class JsonBuilder(builder: JsonBuilder.() -> Unit) {
    val result = JsonObject()

    init {
        builder()
    }

    infix fun String.to(property: Number) = result.addProperty(this, property)
    infix fun String.to(property: Char) = result.addProperty(this, property)
    infix fun String.to(property: Boolean) = result.addProperty(this, property)
    infix fun String.to(property: String) = result.addProperty(this, property)
    infix fun String.to(property: ResourceLocation) = result.addProperty(this, property.toString())
    infix fun String.to(property: IForgeRegistryEntry<*>) = result.addProperty(this, property.registryName.toString())
    infix fun String.to(property: JsonElement) = result.add(this, property)
    infix fun String.to(property: ItemStack) = result.add(this, property.toJson())
    infix fun String.to(properties: Collection<JsonElement>) = result.add(this, properties.toJson())

    infix fun String.to(properties: Array<JsonElement>) {
        val arr = JsonArray()
        properties.forEach(arr::add)
        result.add(this, arr)
    }

    operator fun String.invoke(builder: JsonBuilder.() -> Unit) = result.add(this, JsonBuilder(builder).result)

    fun JsonObject.extract() = this.entrySet().forEach { (key, value) ->
        result.add(key, value)
    }
}

fun json(builder: JsonBuilder.() -> Unit) = JsonBuilder(builder).result