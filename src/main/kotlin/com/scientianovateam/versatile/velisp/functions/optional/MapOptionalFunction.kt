package com.scientianovateam.versatile.velisp.functions.optional

import com.scientianovateam.versatile.velisp.evaluated.NullValue
import com.scientianovateam.versatile.velisp.evaluated.OptionalValue
import com.scientianovateam.versatile.velisp.evaluated.SomeValue
import com.scientianovateam.versatile.velisp.evaluated.callFunction
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object MapOptionalFunction : IFunction {
    override val name = "versatile/map_optional"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = when (val optional = inputs[0].evaluate() as OptionalValue) {
        is SomeValue -> inputs[1].evaluate().callFunction(optional.evaluatedValue)
        is NullValue -> NullValue
    }
}