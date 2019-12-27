package com.scientianovateam.versatile.velisp.functions.arithemetic

import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate
import kotlin.math.pow
import kotlin.math.sqrt

object RootFunction : IFunction {
    override val name = "versatile/root"
    override val inputCount = 1..2
    override fun evaluate(inputs: List<IUnresolved>) =
            if (inputs.size == 1) NumberValue(sqrt(inputs.first().evaluate().value as Double))
            else NumberValue((inputs.first().evaluate().value as Double).pow(1 / inputs.last().evaluate().value as Double))
}