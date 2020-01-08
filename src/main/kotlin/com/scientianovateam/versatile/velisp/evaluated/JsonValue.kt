package com.scientianovateam.versatile.velisp.evaluated

import com.google.gson.JsonObject
import com.scientianovateam.versatile.velisp.types.JSON

class JsonValue(override val value: JsonObject) : IEvaluated {
    override val type = JSON
    override fun toJson() = value
}