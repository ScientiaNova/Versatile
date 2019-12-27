package com.scientianovateam.versatile.velisp.functions.optional

import com.scientianovateam.versatile.velisp.evaluated.NullValue
import com.scientianovateam.versatile.velisp.evaluated.OptionalValue
import com.scientianovateam.versatile.velisp.evaluated.SomeValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object ExpectFunction : IFunction {
    override val name = "versatile/expect"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = when (val optional = inputs.first().evaluate() as OptionalValue) {
        is SomeValue -> optional.evaluatedValue
        is NullValue -> error(inputs.last().evaluate().value.toString())
    }
}