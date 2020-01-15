package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object TakeLastFunction : IFunction {
    override val name = "versatile/take_last"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = ListValue((inputs[0].evaluate() as ListValue).value.takeLast(inputs.last().evaluate().value as Int))
}