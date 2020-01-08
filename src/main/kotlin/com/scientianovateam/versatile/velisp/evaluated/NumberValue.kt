package com.scientianovateam.versatile.velisp.evaluated

import com.google.gson.JsonPrimitive
import com.scientianovateam.versatile.velisp.types.NUMBER

class NumberValue(override val value: Double) : IEvaluated {
    constructor(value: Int) : this(value.toDouble())

    override val type = NUMBER
    override fun toJson() = JsonPrimitive(value)
}