package com.scientianovateam.versatile.velisp.functions.string

import com.scientianovateam.versatile.velisp.evaluated.StringValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object RepeatFunction : IFunction {
    override val name = "versatile/repeat"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = StringValue(inputs.first().evaluate().value.toString().repeat(inputs.last().evaluate().value as Int))
}