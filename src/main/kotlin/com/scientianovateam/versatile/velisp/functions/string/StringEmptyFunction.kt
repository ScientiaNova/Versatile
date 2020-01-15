package com.scientianovateam.versatile.velisp.functions.string

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object StringEmptyFunction : IFunction {
    override val name = "versatile/string_empty"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue(inputs[0].evaluate().value.toString().isEmpty())
}