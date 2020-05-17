package com.scientianova.versatile.common.registry

interface StringRegistryEvent<T> {
    val registry: MutableStringRegistry<T>
}