package com.scientianova.versatile.common.extensions

val Int.alpha get() = (this and 0xFF000000.toInt()) shr 24
val Int.red get() = (this and 0xFF0000) shr 16
val Int.green get() = (this and 0xFF00) shr 8
val Int.blue get() = this and 0xFF

val Int.alphaF get() = alpha / 255f
val Int.redF get() = red / 255f
val Int.greenF get() = green / 255f
val Int.blueF get() = blue / 255f