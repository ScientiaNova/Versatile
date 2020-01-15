package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object NthUncheckedFunction : IFunction {
    override val name = "versatile/nth_unchecked"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = (inputs[0].evaluate() as ListValue).value[inputs.last().evaluate().value as Int]
}