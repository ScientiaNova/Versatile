package com.scientianovateam.versatile.velisp.functions.logic

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object EqualFunction : IFunction {
    override val name = "versatile/equal"
    override val symbol = "="
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue(inputs.first().evaluate().value.toString() == inputs.last().evaluate().value.toString())
}