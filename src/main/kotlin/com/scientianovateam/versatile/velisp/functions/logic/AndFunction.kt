package com.scientianovateam.versatile.velisp.functions.logic

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object AndFunction : IFunction {
    override val inputCount = 2..Int.MAX_VALUE
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue(inputs.any { it.evaluate().value == true })
}