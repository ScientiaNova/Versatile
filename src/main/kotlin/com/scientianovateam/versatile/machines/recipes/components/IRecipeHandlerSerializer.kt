package com.scientianovateam.versatile.machines.recipes.components

import com.google.gson.JsonElement
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.common.serialization.SerializerRegistries
import net.minecraft.util.ResourceLocation

abstract class IRecipeHandlerSerializer<T : IRecipeComponentHandler<*>>(final override val registryName: ResourceLocation) : IRegisterableJSONSerializer<T, JsonElement> {
    init {
        SerializerRegistries.RECIPE_COMPONENT_HANDLER_SERIALIZERS[registryName] = this
    }
}