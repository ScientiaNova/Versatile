package com.scientianova.versatile.common.registry

import kotlin.reflect.KProperty

class StringRegistryObject<T : Any>(private val name: String, private val registry: () -> StringBasedRegistry<T>) {
    operator fun <R> getValue(thisRef: R, property: KProperty<*>): T = registry()[name]!!
    fun get() = registry()[name]!!
}