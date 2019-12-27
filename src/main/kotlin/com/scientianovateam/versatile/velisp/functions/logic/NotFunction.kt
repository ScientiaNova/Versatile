package com.scientianovateam.versatile.velisp.functions.logic

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object NotFunction : IFunction {
    override val name = "versatile/not"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue(!(inputs.first().evaluate().value as Boolean))
}