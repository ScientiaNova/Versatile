package com.scientianovateam.versatile.common.registry

import com.scientianovateam.versatile.Versatile
import net.minecraft.util.ResourceLocation

open class Registry<T : Any> : Iterable<T> {
    private val map = mutableMapOf<ResourceLocation, T>()

    operator fun get(registryName: ResourceLocation) = map[registryName]

    operator fun set(registryName: ResourceLocation, thing: T) {
        if (registryName in map) Versatile.LOGGER.warn("Overriding registry: $registryName")
        map[registryName] = thing
    }

    operator fun contains(registryName: ResourceLocation) = registryName in map

    override fun iterator(): Iterator<T> = map.values.iterator()
}