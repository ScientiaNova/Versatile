package com.scientianovateam.versatile.velisp.functions.string

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object StartsWithFunction : IFunction {
    override val name = "versatile/starts_with"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue(inputs.first().evaluate().value.toString().startsWith(inputs.last().evaluate().value.toString()))
}