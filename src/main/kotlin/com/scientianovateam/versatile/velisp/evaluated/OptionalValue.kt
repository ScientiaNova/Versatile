package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.velisp.NothingType
import com.scientianovateam.versatile.velisp.OptionalType
import java.util.*

sealed class OptionalValue : IEvaluated

object NullValue : OptionalValue() {
    override val value = Optional.empty<Any>()
    override val type = OptionalType(NothingType)
}

data class SomeValue(val evaluatedValue: IEvaluated) : OptionalValue() {
    override val value = Optional.of(evaluatedValue.value)
    override val type = OptionalType(evaluatedValue.type)
}