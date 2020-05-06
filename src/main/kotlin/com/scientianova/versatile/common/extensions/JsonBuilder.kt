package com.scientianova.versatile.common.extensions

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import net.minecraft.item.ItemStack

class JsonBuilder() {
    val result = JsonObject()

    infix fun String.to(property: Number) = result.addProperty(this, property)
    infix fun String.to(property: Char) = result.addProperty(this, property)
    infix fun String.to(property: Boolean) = result.addProperty(this, property)
    infix fun String.to(property: String) = result.addProperty(this, property)
    infix fun String.to(property: JsonElement) = result.add(this, property)
    infix fun String.to(property: ItemStack) = result.add(this, property.toJson())
    infix fun String.to(properties: Collection<JsonElement>) {
        val arr = JsonArray()
        properties.forEach(arr::add)
        result.add(this, arr)
    }
    infix fun String.to(properties: Array<JsonElement>) {
        val arr = JsonArray()
        properties.forEach(arr::add)
        result.add(this, arr)
    }

    inline operator fun String.invoke(builder: JsonBuilder.() -> Unit) =
            result.add(this, JsonBuilder().apply(builder).result)
}

inline fun json(builder: JsonBuilder.() -> Unit) = JsonBuilder().apply(builder).result

fun Number.toJson() = JsonPrimitive(this)
fun Char.toJson() = JsonPrimitive(this)
fun Boolean.toJson() = JsonPrimitive(this)
fun String.toJson() = JsonPrimitive(this)
fun ItemStack.toJson() = json {
    "item" to item.registryName!!.toString()
    "count" to count
}