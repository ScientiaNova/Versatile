package com.scientianovateam.versatile.velisp.functions.string

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object StringContainsFunction : IFunction {
    override val name = "versatile/string_contains"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue(inputs.first().evaluate().value.toString().contains(inputs.last().evaluate().value.toString()))
}