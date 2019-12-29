package com.scientianovateam.versatile.common.extensions

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import net.minecraft.item.ItemStack

fun JsonObject.getObjectOrNull(name: String): JsonObject? = get(name) as? JsonObject

fun JsonObject.getArrayOrNull(name: String): JsonArray? = get(name) as? JsonArray

fun JsonObject.getPrimitiveOrNull(name: String): JsonPrimitive? = get(name) as? JsonPrimitive

fun JsonObject.getStringOrNull(name: String): String? = getPrimitiveOrNull(name)?.asString

fun JsonObject.merge(target: JsonObject): JsonObject = copy().also { new ->
    target.entrySet().forEach { (key, value) ->
        new.add(key, when (value) {
            is JsonObject -> new.getObjectOrNull(key)?.merge(value) ?: value
            else -> value
        })
    }
}

fun JsonObject.copy(): JsonObject = JsonObject().also { new ->
    entrySet().forEach { (key, value) -> new.add(key, value.copy()) }
}

fun JsonArray.copy(): JsonArray = JsonArray().also { new -> forEach { new.add(it.copy()) } }

fun JsonElement.copy(): JsonElement = when (this) {
    is JsonObject -> copy()
    is JsonArray -> copy()
    else -> this
}

fun Number.toJson() = JsonPrimitive(this)

fun Char.toJson() = JsonPrimitive(this)

fun Boolean.toJson() = JsonPrimitive(this)

fun String.toJson() = JsonPrimitive(this)

fun ItemStack.toJson() = json {
    "item" to item.registryName!!.toString()
    "count" to count
}

fun Iterable<JsonElement>.toJson() = JsonArray().apply { this@toJson.forEach { this.add(it) } }