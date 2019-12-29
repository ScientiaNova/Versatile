package com.scientianovateam.versatile.common.loaders.internal

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.scientianovateam.versatile.common.extensions.merge
import kotlin.math.max
import kotlin.math.min

fun cascadeJsons(resources: List<JsonObject>): JsonObject {
    resources.forEach { json ->
        val priority = json.get("priority")
        val namespace = json.get("namespace").asString!!
        val updated = min(PriorityStore.maximums[namespace] ?: Int.MAX_VALUE,
                max(PriorityStore.minimums[namespace] ?: Int.MIN_VALUE,
                        when {
                            priority is JsonPrimitive && priority.isNumber -> priority.asInt
                            else -> 3
                        }))
        json.addProperty("priority", updated)
    }
    return resources.fold(JsonObject(), { a, b -> a.merge(b) })
}