package com.scientianovateam.versatile.velisp.functions.optional

import com.scientianovateam.versatile.velisp.evaluated.NullValue
import com.scientianovateam.versatile.velisp.evaluated.OptionalValue
import com.scientianovateam.versatile.velisp.evaluated.SomeValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object UnwrapFunction : IFunction {
    override val name = "versatile/unwrap"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = when (val optional = inputs.first().evaluate() as OptionalValue) {
        is SomeValue -> optional.evaluatedValue
        is NullValue -> error("Tried to unwrap null value")
    }
}