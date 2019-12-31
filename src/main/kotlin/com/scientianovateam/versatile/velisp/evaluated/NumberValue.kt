package com.scientianovateam.versatile.velisp.evaluated

import com.google.gson.JsonPrimitive
import com.scientianovateam.versatile.velisp.NumberType

class NumberValue(override val value: Double) : IEvaluated {
    constructor(value: Int) : this(value.toDouble())

    override val type = NumberType
    override fun toJson() = JsonPrimitive(value)
}