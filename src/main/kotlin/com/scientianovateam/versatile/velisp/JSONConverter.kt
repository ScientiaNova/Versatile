package com.scientianovateam.versatile.velisp

import com.google.gson.*
import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.NullValue
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.evaluated.StringValue
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall
import com.scientianovateam.versatile.velisp.unresolved.Getter
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

fun convertJSON(json: JsonElement): IUnresolved = when (json) {
    is JsonArray -> FunctionCall((((json.firstOrNull()
            ?: error("Empty JSON array")) as? JsonPrimitive)?.asString
            ?: error("Invalid start of a function call")), json.drop(1).map(::convertJSON))
    is JsonPrimitive -> when {
        json.isNumber -> NumberValue(json.asDouble)
        json.isBoolean -> BoolValue(json.asBoolean)
        else -> if (json.asString.startsWith('$')) Getter(json.asString) else StringValue(json.asString)
    }
    is JsonObject -> error("Can't use a JSON object in an expression")
    is JsonNull -> NullValue
    else -> error("Invalid JSON expression")
}