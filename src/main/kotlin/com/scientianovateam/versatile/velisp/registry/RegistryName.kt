package com.scientianovateam.versatile.velisp.registry

import com.scientianovateam.versatile.Versatile

data class RegistryName(val modId: String, val name: String) {
    override fun toString() = "$modId/$name"
}

fun String.toRegName(): RegistryName {
    val split = split('/', limit = 2)
    return if (split.size == 1) RegistryName(Versatile.MOD_ID, split[0]) else RegistryName(split[0], split[1])
}