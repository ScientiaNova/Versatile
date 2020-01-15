package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object SliceFunction : IFunction {
    override val name = "versatile/slice"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = ListValue((inputs[0].evaluate() as ListValue).value.slice((inputs.last() as ListValue).value.map { it.value as Int }))
}