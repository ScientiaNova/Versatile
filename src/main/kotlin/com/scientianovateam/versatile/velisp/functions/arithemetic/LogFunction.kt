package com.scientianovateam.versatile.velisp.functions.arithemetic

import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate
import kotlin.math.ln
import kotlin.math.log

object LogFunction : IFunction {
    override val name = "versatile/log"
    override val inputCount = 1..2
    override fun evaluate(inputs: List<IUnresolved>) =
            if (inputs.size == 1) NumberValue(ln(inputs[0].evaluate().value as Double))
            else NumberValue(log(inputs[0].evaluate().value as Double, inputs[1].evaluate().value as Double))
}