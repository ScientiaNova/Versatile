package com.scientianovateam.versatile.velisp.functions.logic

import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.expr
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object IsPositiveFunction : IFunction {
    override val name = "versatile/is_positive"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = ((inputs.first().evaluate() as NumberValue).value > 0).expr()
}