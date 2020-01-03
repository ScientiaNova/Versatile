package com.scientianovateam.versatile.items.properties

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getPrimitiveOrNull
import com.scientianovateam.versatile.common.extensions.getStringOrNull
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import net.minecraft.potion.EffectInstance
import net.minecraftforge.registries.ForgeRegistries

object EffectInstanceSerializer : IJSONSerializer<EffectInstance, JsonObject> {
    override fun read(json: JsonObject) = EffectInstance(
            json.getStringOrNull("effect")?.let { ForgeRegistries.POTIONS.getValue(it.toResLoc()) }
                    ?: error("Didn't specify potion effect for potion instance"),
            json.getPrimitiveOrNull("duration")?.asInt ?: 0,
            json.getPrimitiveOrNull("level")?.asInt ?: 0,
            json.getPrimitiveOrNull("is_ambient")?.asBoolean ?: false,
            json.getPrimitiveOrNull("show_particles")?.isBoolean ?: true,
            json.getPrimitiveOrNull("show_icon")?.asBoolean ?: true
    )

    override fun write(obj: EffectInstance) = json {
        "effect" to obj.potion.registryName!!.toString()
        "duration" to obj.duration
        "level" to obj.amplifier
        "Is_ambient" to obj.isAmbient
        "show_particles" to obj.doesShowParticles()
        "show_icon" to obj.isShowIcon
    }
}