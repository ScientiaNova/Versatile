package com.scientianovateam.versatile.velisp.evaluated

import com.google.gson.JsonPrimitive
import com.scientianovateam.versatile.common.extensions.shorten
import com.scientianovateam.versatile.velisp.types.NUMBER

class NumberValue(override val value: Double) : IEvaluated {
    constructor(value: Int) : this(value.toDouble())

    override val type = NUMBER
    override fun serialize() = JsonPrimitive(value.shorten())

    companion object {
        @JvmField
        val ZERO = NumberValue(0)
        @JvmField
        val ONE = NumberValue(1)
        @JvmField
        val BYTE_LIMIT = NumberValue(255)
    }
}