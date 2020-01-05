package com.scientianovateam.versatile.blocks.properties

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getFloatOrNull
import com.scientianovateam.versatile.common.extensions.getStringOrNull
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import net.minecraft.block.SoundType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.registries.ForgeRegistries

class SoundTypeV(val registryName: ResourceLocation, volume: Float = 1f, pitch: Float = 1f, breakSoundSupplier: () -> SoundEvent, stepSoundSupplier: () -> SoundEvent, placeSoundSupplier: () -> SoundEvent, hitSoundSupplier: () -> SoundEvent, fallSoundSupplier: () -> SoundEvent) : SoundType(volume, pitch, null, null, null, null, null) {
    constructor(registryName: ResourceLocation, volume: Float = 1f, pitch: Float = 1f, breakSoundName: ResourceLocation, stepSoundName: ResourceLocation, placeSoundName: ResourceLocation, hitSoundName: ResourceLocation, fallSoundName: ResourceLocation) :
            this(registryName, volume, pitch, { ForgeRegistries.SOUND_EVENTS.getValue(breakSoundName)!! }, { ForgeRegistries.SOUND_EVENTS.getValue(stepSoundName)!! }, { ForgeRegistries.SOUND_EVENTS.getValue(placeSoundName)!! }, { ForgeRegistries.SOUND_EVENTS.getValue(hitSoundName)!! }, { ForgeRegistries.SOUND_EVENTS.getValue(fallSoundName)!! })

    private val breakSoundEvent by lazy(breakSoundSupplier)
    private val stepSoundEvent by lazy(stepSoundSupplier)
    private val placeSoundEvent by lazy(placeSoundSupplier)
    private val hitSoundEvent by lazy(hitSoundSupplier)
    private val fallSoundEvent by lazy(fallSoundSupplier)

    override fun getBreakSound() = breakSoundEvent
    override fun getStepSound() = stepSoundEvent
    override fun getPlaceSound() = placeSoundEvent
    override fun getHitSound() = hitSoundEvent
    override fun getFallSound() = fallSoundEvent

    object Serializer : IJSONSerializer<SoundTypeV, JsonObject> {
        override fun read(json: JsonObject) = SoundTypeV(
                registryName = json.getStringOrNull("name")?.toResLoc() ?: error("missing sound type name"),
                volume = json.getFloatOrNull("volume") ?: 1f,
                pitch = json.getFloatOrNull("pitch") ?: 1f,
                breakSoundName = json.getStringOrNull("break_sound")?.toResLoc()
                        ?: error("missing break sound for sound type"),
                stepSoundName = json.getStringOrNull("step_sound")?.toResLoc()
                        ?: error("missing step sound for sound type"),
                placeSoundName = json.getStringOrNull("place_sound")?.toResLoc()
                        ?: error("missing place sound for sound type"),
                hitSoundName = json.getStringOrNull("hit_sound")?.toResLoc()
                        ?: error("missing hit sound for sound type"),
                fallSoundName = json.getStringOrNull("fall_sound")?.toResLoc()
                        ?: error("missing fall sound for sound type")
        )

        override fun write(obj: SoundTypeV) = json {
            "volume" to obj.volume
            "pitch" to obj.pitch
            "break_sound" to obj.fallSound
            "step_sound" to obj.stepSound
            "place_sound" to obj.placeSound
            "hit_sound" to obj.hitSound
            "fall_sound" to obj.fallSound
        }
    }
}