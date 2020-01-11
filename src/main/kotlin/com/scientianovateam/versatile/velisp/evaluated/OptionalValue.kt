package com.scientianovateam.versatile.velisp.evaluated

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.scientianovateam.versatile.velisp.functions.constructor.SomeFunction
import com.scientianovateam.versatile.velisp.types.NOTHING
import com.scientianovateam.versatile.velisp.types.OptionalType
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall
import java.util.*

sealed class OptionalValue : IEvaluated

object NullValue : OptionalValue() {
    override val value = Optional.empty<Any>()
    override val type = OptionalType(NOTHING)
    override fun serialize(): JsonNull = JsonNull.INSTANCE
    override fun toJSON() = null
}

data class SomeValue(val evaluatedValue: IEvaluated) : OptionalValue() {
    override val value = Optional.of(evaluatedValue.value)
    override val type = OptionalType(evaluatedValue.type)
    override fun serialize() = FunctionCall(SomeFunction.name, listOf(evaluatedValue)).serialize()
    override fun toJSON() = evaluatedValue.toJSON()
}