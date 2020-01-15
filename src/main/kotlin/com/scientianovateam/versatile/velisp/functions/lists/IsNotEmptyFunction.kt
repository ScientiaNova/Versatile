package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object IsNotEmptyFunction : IFunction {
    override val name = "versatile/is_not_empty"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue((inputs[0].evaluate() as ListValue).value.isNotEmpty())
}