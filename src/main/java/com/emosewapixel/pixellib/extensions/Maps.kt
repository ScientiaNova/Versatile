package com.emosewapixel.pixellib.extensions

import com.google.common.reflect.MutableTypeToInstanceMap

fun <K, V> MutableMap<K, V>.getOrAdd(key: K, default: V) = get(key) ?: default.also { put(key, default) }
fun <T> MutableTypeToInstanceMap<in T>.getOrAddInstance(key: Class<T>, default: T) = getInstance(key)
        ?: default.also { putInstance(key, default) }