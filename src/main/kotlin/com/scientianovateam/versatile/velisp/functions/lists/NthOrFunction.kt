package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object NthOrFunction : IFunction {
    override val name = "versatile/nth_or"
    override val inputCount = 3..3
    override fun evaluate(inputs: List<IUnresolved>) = (inputs[0].evaluate() as ListValue).value.getOrNull(inputs[1].evaluate().value as Int)
            ?: inputs[2].evaluate()
}