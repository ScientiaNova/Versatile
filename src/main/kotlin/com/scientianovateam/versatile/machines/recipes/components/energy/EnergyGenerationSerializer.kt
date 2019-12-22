package com.scientianovateam.versatile.machines.recipes.components.energy

import com.scientianovateam.versatile.common.extensions.toJson
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.machines.recipes.components.IRecipeHandlerSerializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive

object EnergyGenerationSerializer : IRecipeHandlerSerializer<EnergyGenerationHandler>("versatile:energy_generated_per_tick".toResLoc()) {
    override fun write(obj: EnergyGenerationHandler) = obj.value.toJson()

    override fun read(json: JsonElement) = EnergyGenerationHandler(if (json is JsonPrimitive) when {
        json.isNumber -> json.asNumber.toInt()
        else -> 0
    }
    else 0)
}