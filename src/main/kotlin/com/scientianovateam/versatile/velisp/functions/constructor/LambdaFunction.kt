package com.scientianovateam.versatile.velisp.functions.constructor

import com.scientianovateam.versatile.velisp.evaluated.FunctionValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object LambdaFunction : IFunction {
    override val name = "versatile/func"
    override val inputCount = 1..Int.MAX_VALUE
    override fun evaluate(inputs: List<IUnresolved>) = FunctionValue(inputs.dropLast(1).map { it.evaluate().value.toString() }, inputs.last())
}