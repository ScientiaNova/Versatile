package com.scientianovateam.versatile.velisp.evaluated

import com.google.gson.JsonPrimitive
import com.scientianovateam.versatile.velisp.types.STRING_TYPE
import com.scientianovateam.versatile.velisp.types.StringType

class StringValue(override val value: String) : IEvaluated {
    override val type = STRING_TYPE
    override fun toJson() = JsonPrimitive(value)
}