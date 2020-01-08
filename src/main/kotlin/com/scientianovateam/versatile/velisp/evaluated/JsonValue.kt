package com.scientianovateam.versatile.velisp.evaluated

import com.google.gson.JsonObject
import com.scientianovateam.versatile.velisp.types.JSON_TYPE

class JsonValue(override val value: JsonObject) : IEvaluated {
    override val type = JSON_TYPE
    override fun toJson() = value
}