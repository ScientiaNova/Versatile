package com.scientianovateam.versatile.machines.recipes.components.energy

import com.scientianovateam.versatile.common.extensions.toJson
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.machines.recipes.components.IRecipeHandlerSerializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive

object EnergyConsumptionSerializer : IRecipeHandlerSerializer<EnergyConsumptionHandler>("versatile:energy_consumed_per_tick".toResLoc()) {
    override fun write(obj: EnergyConsumptionHandler) = obj.value.toJson()

    override fun read(json: JsonElement) = EnergyConsumptionHandler(if (json is JsonPrimitive) when {
        json.isNumber -> json.asNumber.toInt()
        else -> 0
    }
    else 0)
}