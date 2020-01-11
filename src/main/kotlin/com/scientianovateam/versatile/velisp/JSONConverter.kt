package com.scientianovateam.versatile.velisp

import com.google.gson.*
import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.NullValue
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.evaluated.StringValue
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall
import com.scientianovateam.versatile.velisp.unevaluated.UnevaluatedStructValue
import com.scientianovateam.versatile.velisp.unresolved.Getter
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

fun JsonElement.toExpression(): IUnresolved = when (this) {
    is JsonArray -> FunctionCall((((this.firstOrNull()
            ?: error("Empty JSON array")) as? JsonPrimitive)?.asString
            ?: error("Invalid start of a function call")), this.drop(1).map(JsonElement::toExpression))
    is JsonPrimitive -> when {
        this.isNumber -> NumberValue(this.asDouble)
        this.isBoolean -> BoolValue(this.asBoolean)
        else -> if (this.asString.startsWith('$')) Getter(this.asString) else StringValue(this.asString)
    }
    is JsonObject -> UnevaluatedStructValue(this.entrySet().map { (key, value) -> key to value.toExpression() }.toMap())
    is JsonNull -> NullValue
    else -> error("Invalid JSON expression")
}