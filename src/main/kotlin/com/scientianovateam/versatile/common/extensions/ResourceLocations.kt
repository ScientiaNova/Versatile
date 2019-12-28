package com.scientianovateam.versatile.common.extensions

import com.scientianovateam.versatile.Versatile
import net.minecraft.util.ResourceLocation

operator fun ResourceLocation.component1(): String = namespace
operator fun ResourceLocation.component2(): String = path
operator fun ResourceLocation.plus(extra: String) = ResourceLocation(namespace, path + extra)

fun String.toResLoc(baseNamespace: String = "minecraft", separator: Char = ':'): ResourceLocation {
    val parts = this.split(separator, limit = 1)
    return if (parts.size == 1) ResourceLocation(baseNamespace, parts.first()) else ResourceLocation(parts.first(), parts.last())
}

fun String.toResLocV(separator: Char = ':'): ResourceLocation {
    val parts = this.split(separator, limit = 1)
    return if (parts.size == 1) ResourceLocation(Versatile.MOD_ID, parts.first()) else ResourceLocation(parts.first(), parts.last())
}