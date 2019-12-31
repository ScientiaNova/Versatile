package com.scientianovateam.versatile.velisp.evaluated

import com.google.gson.JsonNull
import com.scientianovateam.versatile.velisp.NothingType
import com.scientianovateam.versatile.velisp.OptionalType
import com.scientianovateam.versatile.velisp.functions.constructor.SomeFunction
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall
import java.util.*

sealed class OptionalValue : IEvaluated

object NullValue : OptionalValue() {
    override val value = Optional.empty<Any>()
    override val type = OptionalType(NothingType)
    override fun toJson(): JsonNull = JsonNull.INSTANCE
}

data class SomeValue(val evaluatedValue: IEvaluated) : OptionalValue() {
    override val value = Optional.of(evaluatedValue.value)
    override val type = OptionalType(evaluatedValue.type)
    override fun toJson() = FunctionCall(SomeFunction.name, listOf(evaluatedValue)).toJson()
}