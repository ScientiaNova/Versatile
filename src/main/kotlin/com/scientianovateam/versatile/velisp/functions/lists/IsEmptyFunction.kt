package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.evaluated.function
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object IsEmptyFunction : IFunction {
    override val name = "versatile/is_empty"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue((inputs.first().evaluate() as ListValue).value.isEmpty())
}