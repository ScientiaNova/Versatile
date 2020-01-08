package com.scientianovateam.versatile.velisp.evaluated

import com.google.gson.JsonPrimitive
import com.scientianovateam.versatile.velisp.types.STRING

class StringValue(override val value: String) : IEvaluated {
    override val type = STRING
    override fun toJson() = JsonPrimitive(value)
}