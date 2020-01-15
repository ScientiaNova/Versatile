package com.scientianovateam.versatile.velisp.functions.logic

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object GreaterOrEqualFunction : IFunction {
    override val name = "versatile/greater_or_equal"
    override val symbol = ">="
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue(inputs[0].evaluate().value as Double >= inputs[1].evaluate().value as Double)
}