package com.scientianovateam.versatile.common.registry

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.extensions.toResLocV
import net.minecraft.util.ResourceLocation

open class Registry<T : Any> : Iterable<Map.Entry<ResourceLocation, T>> {
    private val map = mutableMapOf<ResourceLocation, T>()

    operator fun get(registryName: ResourceLocation) = map[registryName]

    operator fun get(registryName: String) = map[registryName.toResLocV()]

    operator fun set(registryName: ResourceLocation, thing: T) {
        if (registryName in map) Versatile.LOGGER.warn("Overriding registry: $registryName")
        map[registryName] = thing
    }

    operator fun set(registryName: String, thing: T) = set(registryName.toResLocV(), thing)

    operator fun contains(registryName: ResourceLocation) = registryName in map

    override fun iterator(): Iterator<Map.Entry<ResourceLocation, T>> = map.iterator()
}