package com.scientianovateam.versatile.velisp.registry

import com.scientianovateam.versatile.Versatile

open class VELISPRegistry<T : Any> : Iterable<Map.Entry<RegistryName, T>> {
    private val map = mutableMapOf<RegistryName, T>()

    operator fun get(registryName: RegistryName) = map[registryName]

    operator fun get(registryName: String) = map[registryName.toRegName()]

    operator fun set(registryName: RegistryName, thing: T) {
        if (registryName in map) Versatile.LOGGER.warn("Overriding registry: $registryName")
        map[registryName] = thing
    }

    operator fun set(registryName: String, thing: T) = set(registryName.toRegName(), thing)

    operator fun contains(registryName: RegistryName) = registryName in map

    override fun iterator(): Iterator<Map.Entry<RegistryName, T>> = map.iterator()
}