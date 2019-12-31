package com.scientianovateam.versatile.common.serialization

import com.google.gson.JsonElement
import net.minecraft.util.ResourceLocation

interface IRegisterableJSONSerializer<T, J : JsonElement> : IJSONSerializer<T, J> {
    val registryName: ResourceLocation
}