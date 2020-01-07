package com.scientianovateam.versatile.velisp.evaluated

import com.google.gson.JsonNull
import com.scientianovateam.versatile.velisp.functions.constructor.SomeFunction
import com.scientianovateam.versatile.velisp.types.NOTHING_TYPE
import com.scientianovateam.versatile.velisp.types.OptionalType
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall
import java.util.*

sealed class OptionalValue : IEvaluated

object NullValue : OptionalValue() {
    override val value = Optional.empty<Any>()
    override val type = OptionalType(NOTHING_TYPE)
    override fun toJson(): JsonNull = JsonNull.INSTANCE
}

data class SomeValue(val evaluatedValue: IEvaluated) : OptionalValue() {
    override val value = Optional.of(evaluatedValue.value)
    override val type = OptionalType(evaluatedValue.type)
    override fun toJson() = FunctionCall(SomeFunction.name, listOf(evaluatedValue)).toJson()
}