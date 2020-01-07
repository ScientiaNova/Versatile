package com.scientianovateam.versatile.velisp.functions.logic

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object GreaterFunction : IFunction {
    override val name = "versatile/greater"
    override val symbol = ">"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue(inputs.first().evaluate().value as Double > inputs.last().evaluate().value as Double)
}