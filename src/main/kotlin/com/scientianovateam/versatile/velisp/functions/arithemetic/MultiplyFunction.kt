package com.scientianovateam.versatile.velisp.functions.arithemetic

import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object MultiplyFunction : IFunction {
    override val inputCount = 2..Int.MAX_VALUE
    override fun evaluate(inputs: List<IUnresolved>) = NumberValue(inputs.map { it.evaluate().value as Double }.fold(1.0) { acc, num -> acc + num })
}