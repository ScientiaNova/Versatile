package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object RandomElementFunction : IFunction {
    override val name = "versatile/random_element"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = (inputs.first().evaluate() as ListValue).value.random()
}