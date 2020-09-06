package com.scientianova.versatile.common.registry

interface MutableStringRegistry<T> {
    operator fun get(name: String): T?
    fun register(thing: T)
}