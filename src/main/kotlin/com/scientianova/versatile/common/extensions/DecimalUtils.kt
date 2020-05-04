package com.scientianova.versatile.common.extensions

import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.roundToLong

fun Float.round(decimal: Int = 1) = (this * 10.0.pow(decimal)).roundToLong() / 10.0.pow(decimal)
fun Double.round(decimal: Int = 1) = (this * 10.0.pow(decimal)).roundToLong() / 10.0.pow(decimal)

fun Float.shorten(): Number = if (this == ceil(this)) toInt() else this
fun Double.shorten(): Number = if (this == ceil(this)) toInt() else this