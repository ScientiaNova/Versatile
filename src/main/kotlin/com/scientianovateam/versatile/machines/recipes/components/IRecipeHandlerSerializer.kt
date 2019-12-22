package com.scientianovateam.versatile.machines.recipes.components

import com.scientianovateam.versatile.common.registry.BaseRegistries
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.google.gson.JsonElement
import net.minecraft.util.ResourceLocation

abstract class IRecipeHandlerSerializer<T : IRecipeComponentHandler<*>>(final override val registryName: ResourceLocation) : IRegisterableJSONSerializer<T, JsonElement> {
    init {
        BaseRegistries.RECIPE_COMPONENT_HANDLER_SERIALIZERS[registryName] = this
    }
}