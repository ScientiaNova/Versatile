package com.scientianovateam.versatile.velisp.evaluated

import com.google.gson.JsonPrimitive
import com.scientianovateam.versatile.velisp.types.BOOL_TYPE

class BoolValue(override val value: Boolean) : IEvaluated {
    override val type = BOOL_TYPE

    override fun toJson() = JsonPrimitive(value)

    companion object {
        val TRUE = BoolValue(true)
        val FALSE = BoolValue(false)
    }
}