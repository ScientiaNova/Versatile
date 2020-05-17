package com.scientianova.versatile.common.registry

import net.minecraft.util.ResourceLocation

open class Registry<T : Any>(private val map: Map<ResourceLocation, T>) : Iterable<T> {
    operator fun get(registryName: ResourceLocation) = map[registryName]
    operator fun contains(registryName: ResourceLocation) = registryName in map
    override fun iterator() = map.values.iterator()
}