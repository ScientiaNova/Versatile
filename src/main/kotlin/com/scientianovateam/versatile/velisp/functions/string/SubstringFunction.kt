package com.scientianovateam.versatile.velisp.functions.string

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.StringValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object SubstringFunction : IFunction {
    override val name = "versatile/substring"
    override val inputCount = 2..3
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        val string = inputs[0].evaluate().value.toString()
        return StringValue(string.substring(inputs[1].evaluate().value as Int, if (inputs.size > 2) inputs[2].evaluate().value as Int else string.length))
    }
}