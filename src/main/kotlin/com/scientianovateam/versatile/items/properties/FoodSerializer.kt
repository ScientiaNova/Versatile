package com.scientianovateam.versatile.items.properties

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getArrayOrNull
import com.scientianovateam.versatile.common.extensions.getPrimitiveOrNull
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import net.minecraft.item.Food
import org.apache.commons.lang3.tuple.Pair

object FoodSerializer : IJSONSerializer<Food, JsonObject> {
    override fun read(json: JsonObject) = LazyFood(
            value = json.getPrimitiveOrNull("hunger")?.asInt ?: 0,
            saturation = json.getPrimitiveOrNull("saturation")?.asFloat ?: 0f,
            isMeat = json.getPrimitiveOrNull("is_meat")?.asBoolean ?: false,
            canEatWhenFull = json.getPrimitiveOrNull("always_edible")?.asBoolean ?: false,
            fastToEat = json.getPrimitiveOrNull("fast_to_eat")?.asBoolean ?: false
    ) {
        json.getArrayOrNull("effects")?.filterIsInstance<JsonObject>()?.map {
            Pair.of(EffectInstanceSerializer.read(it), it.getPrimitiveOrNull("probability")?.asFloat ?: 1f)
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