package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object AverageFunction : IFunction {
    override val name = "versatile/average"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = NumberValue((inputs.first().evaluate() as ListValue).value.map { it.value as Int }.average())
}