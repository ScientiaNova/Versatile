package com.scientianovateam.versatile.common.extensions

import com.google.gson.*


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