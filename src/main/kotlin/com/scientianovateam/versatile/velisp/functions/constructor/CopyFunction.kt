package com.scientianovateam.versatile.velisp.functions.constructor

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object CopyFunction : IFunction {
    override val name = "versatile/copy"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        val value = inputs[1].evaluate()
        return ListValue(List(inputs[0].evaluate().value as Int) { value })
    }
}