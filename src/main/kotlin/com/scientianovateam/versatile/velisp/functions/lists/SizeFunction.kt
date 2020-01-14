package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object SizeFunction : IFunction {
    override val name = "versatile/size"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = NumberValue((inputs[0].evaluate() as ListValue).value.size)
}