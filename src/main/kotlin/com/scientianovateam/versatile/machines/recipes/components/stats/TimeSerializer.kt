package com.scientianovateam.versatile.machines.recipes.components.stats

import com.scientianovateam.versatile.common.extensions.toJson
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.machines.recipes.components.IRecipeHandlerSerializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive

object TimeSerializer : IRecipeHandlerSerializer<TimeHandler>("versatile:time".toResLoc()) {
    override fun write(obj: TimeHandler) = obj.value.toJson()

    override fun read(json: JsonElement) = TimeHandler(if (json is JsonPrimitive) when {
        json.isNumber -> json.asNumber.toInt()
        else -> 0
    }
    else 0)
}