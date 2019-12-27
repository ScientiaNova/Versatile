package com.scientianovateam.versatile.velisp.functions.arithemetic

import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate
import kotlin.math.tan

object TanFunction : IFunction {
    override val name = "versatile/tan"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = NumberValue(tan(inputs.first().evaluate().value as Double))
}