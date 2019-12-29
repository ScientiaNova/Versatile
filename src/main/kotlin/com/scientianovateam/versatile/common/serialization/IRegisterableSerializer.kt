package com.scientianovateam.versatile.common.serialization

import com.google.gson.JsonElement
import net.minecraft.util.ResourceLocation

interface IRegisterableSerializer<T, J : JsonElement> : IGeneralSerializer<T, J> {
    val registryName: ResourceLocation
}