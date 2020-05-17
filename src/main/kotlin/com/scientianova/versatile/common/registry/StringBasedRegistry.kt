package com.scientianova.versatile.common.registry

class StringBasedRegistry<T : Any>(private val map: Map<String, T>) : Iterable<T> {
    operator fun get(name: String) = map[name]
    operator fun contains(name: String) = name in map
    override fun iterator() = map.values.distinct().iterator()
}