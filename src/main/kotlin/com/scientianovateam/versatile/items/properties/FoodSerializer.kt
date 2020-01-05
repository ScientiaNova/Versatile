package com.scientianovateam.versatile.items.properties

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import net.minecraft.item.Food
import org.apache.commons.lang3.tuple.Pair

object FoodSerializer : IJSONSerializer<Food, JsonObject> {
    override fun read(json: JsonObject) = LazyFood(
            value = json.getIntOrNull("hunger") ?: 0,
            saturation = json.getFloatOrNull("saturation") ?: 0f,
            isMeat = json.getBooleanOrNull("is_meat") ?: false,
            canEatWhenFull = json.getBooleanOrNull("always_edible") ?: false,
            fastToEat = json.getBooleanOrNull("fast_to_eat") ?: false
    ) {
        json.getArrayOrNull("effects")?.filterIsInstance<JsonObject>()?.map {
            Pair.of(EffectInstanceSerializer.read(it), it.getFloatOrNull("probability") ?: 1f)
        } ?: emptyList()
    }

    override fun write(obj: Food) = json {
        "hunger" to obj.healing
        "saturation" to obj.saturation
        "is_meat" to obj.isMeat
        "always_edible" to obj.canEatWhenFull()
        "fast_to_eat" to obj.isFastEating
        "effects" to obj.effects.map { EffectInstanceSerializer.write(it.left).apply { addProperty("probability", it.right) } }
    }
}