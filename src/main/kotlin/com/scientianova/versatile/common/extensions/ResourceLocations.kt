package com.scientianova.versatile.common.extensions

import net.minecraft.util.ResourceLocation

operator fun ResourceLocation.component1(): String = namespace
operator fun ResourceLocation.component2(): String = path
operator fun ResourceLocation.plus(extra: String) = ResourceLocation(namespace, path + extra)

fun String.toResLoc() = ResourceLocation(this)
fun String.toResLoc(baseNamespace: String) = if (':' in this) toResLoc() else ResourceLocation(baseNamespace, this)