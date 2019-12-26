package com.scientianovateam.versatile.common.extensions

import net.minecraft.util.ResourceLocation

operator fun ResourceLocation.component1(): String = namespace
operator fun ResourceLocation.component2(): String = path
operator fun ResourceLocation.plus(extra: String) = ResourceLocation(namespace, path + extra)

fun String.toResLoc(baseNamespace: String = "minecraft", separator: Char = ':'): ResourceLocation {
    val parts = this.split(separator, limit = 1)
    return when {
        parts.size == 1 -> ResourceLocation(baseNamespace, parts.first())
        parts.first().isEmpty() -> ResourceLocation(baseNamespace, parts.last())
        else -> ResourceLocation(parts.first(), parts.last())
    }
}