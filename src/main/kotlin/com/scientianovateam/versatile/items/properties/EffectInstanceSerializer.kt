package com.scientianovateam.versatile.items.properties

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import net.minecraft.potion.EffectInstance
import net.minecraftforge.registries.ForgeRegistries

object EffectInstanceSerializer : IJSONSerializer<EffectInstance, JsonObject> {
    override fun read(json: JsonObject) = EffectInstance(
            json.getStringOrNull("effect")?.let { ForgeRegistries.POTIONS.getValue(it.toResLoc()) }
                    ?: error("Didn't specify potion effect for potion instance"),
            json.getIntOrNull("duration") ?: 0,
            json.getIntOrNull("level") ?: 0,
            json.getBooleanOrNull("is_ambient") ?: false,
            json.getBooleanOrNull("show_particles") ?: true,
            json.getBooleanOrNull("show_icon") ?: true
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