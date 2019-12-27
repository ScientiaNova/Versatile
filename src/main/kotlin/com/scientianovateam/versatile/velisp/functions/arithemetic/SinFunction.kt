package com.scientianovateam.versatile.velisp.functions.arithemetic

import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate
import kotlin.math.sin

object SinFunction : IFunction {
    override val name = "versatile/sin"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = NumberValue(sin(inputs.first().evaluate().value as Double))
}