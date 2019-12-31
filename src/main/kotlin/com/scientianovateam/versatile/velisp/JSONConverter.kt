package com.scientianovateam.versatile.velisp

import com.google.gson.*
import com.scientianovateam.versatile.velisp.evaluated.*
import com.scientianovateam.versatile.velisp.functions.constructor.FormFunction
import com.scientianovateam.versatile.velisp.functions.constructor.MaterialFunction
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall
import com.scientianovateam.versatile.velisp.unevaluated.IUnevaluated
import com.scientianovateam.versatile.velisp.unresolved.Getter
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

fun convertToExpression(json: JsonElement): IUnresolved = when (json) {
    is JsonArray -> FunctionCall((((json.firstOrNull()
            ?: error("Empty JSON array")) as? JsonPrimitive)?.asString
            ?: error("Invalid start of a function call")), json.drop(1).map(::convertToExpression))
    is JsonPrimitive -> when {
        json.isNumber -> NumberValue(json.asDouble)
        json.isBoolean -> BoolValue(json.asBoolean)
        else -> if (json.asString.startsWith('$')) Getter(json.asString) else StringValue(json.asString)
    }
    is JsonObject -> error("Can't use a JSON object in an expression")
    is JsonNull -> NullValue
    else -> error("Invalid JSON expression")
}

fun expressionToJson(expression: IUnresolved): JsonElement = when (expression) {
    is BoolValue -> JsonPrimitive(expression.value)
    is FormValue -> expressionToJson(FunctionCall(FormFunction.name, listOf(StringValue(expression.value.name))))
    is MaterialValue -> expressionToJson(FunctionCall(MaterialFunction.name, listOf(StringValue(expression.value.name))))
    is NumberValue -> JsonPrimitive(expression.value)
    is StringValue -> JsonPrimitive(expression.value)
    is FunctionCall -> JsonArray().apply { (listOf(StringValue(expression.function.name)) + expression.inputs).map(::expressionToJson).forEach { add(it) } }
    is Getter -> JsonPrimitive("$" + expression.name)
    else -> error("Unknown type")
}