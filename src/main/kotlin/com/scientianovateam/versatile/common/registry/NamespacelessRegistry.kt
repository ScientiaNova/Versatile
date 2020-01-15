package com.scientianovateam.versatile.common.registry

import com.scientianovateam.versatile.Versatile

open class NamespacelessRegistry<T : Any> : Iterable<T> {
    private val map = mutableMapOf<String, T>()

    operator fun get(registryName: String) = map[registryName]

    operator fun set(registryName: String, thing: T) {
        if (registryName in map) Versatile.LOGGER.warn("Overriding registry: $registryName")
        map[registryName] = thing
    }

    operator fun contains(registryName: String) = registryName in map

    override fun iterator(): Iterator<T> = map.values.iterator()
}