package com.scientianova.versatile.common.registry

import com.scientianova.versatile.Versatile
import net.minecraft.util.ResourceLocation

open class Registry<T: Any> {
    private val map = mutableMapOf<ResourceLocation, T>()

    operator fun get(registryName: ResourceLocation) = map[registryName]

    operator fun set(registryName: ResourceLocation, thing: T) {
        if (registryName in map) _root_ide_package_.com.scientianova.versatile.Versatile.LOGGER.warn("Overriding registry: $registryName")
        map[registryName] = thing
    }

    operator fun contains(registryName: ResourceLocation) = registryName in map
}