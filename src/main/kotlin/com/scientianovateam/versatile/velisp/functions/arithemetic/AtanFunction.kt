package com.scientianovateam.versatile.velisp.functions.arithemetic

import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate
import kotlin.math.atan

object AtanFunction : IFunction {
    override val name = "versatile/atan"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = NumberValue(atan(inputs.first().evaluate().value as Double))
}