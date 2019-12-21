package com.emosewapixel.pixellib.extensions

fun Pair<Int, Int>.max() = kotlin.math.max(first, second)
fun Pair<Long, Long>.max() = kotlin.math.max(first, second)
fun Pair<Float, Float>.max() = kotlin.math.max(first, second)
fun Pair<Double, Double>.max() = kotlin.math.max(first, second)

fun Pair<Int, Int>.min() = kotlin.math.min(first, second)
fun Pair<Long, Long>.min() = kotlin.math.min(first, second)
fun Pair<Float, Float>.min() = kotlin.math.min(first, second)
fun Pair<Double, Double>.min() = kotlin.math.min(first, second)