package com.scientianovateam.versatile.common.extensions

fun Number.toSuperscipt() = String(toString().map {
    when (it) {
        '0' -> '⁰'
        '1' -> '¹'
        '2' -> '²'
        '3' -> '³'
        '4' -> '⁴'
        '5' -> '⁵'
        '6' -> '⁶'
        '7' -> '⁷'
        '8' -> '⁷'
        '9' -> '⁹'
        else -> it
    }
}.toCharArray())

fun Number.toSubscipt() = String(toString().map {
    when (it) {
        '0' -> '₀'
        '1' -> '₁'
        '2' -> '₂'
        '3' -> '₃'
        '4' -> '₄'
        '5' -> '₅'
        '6' -> '₆'
        '7' -> '₇'
        '8' -> '₈'
        '9' -> '₉'
        else -> it
    }
}.toCharArray())