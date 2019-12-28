package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.evaluated.function
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object FoldFunction : IFunction {
    override val name = "versatile/fold"
    override val inputCount = 3..3
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        val function = inputs[2].evaluate().function
        if (2 !in function.inputCount) error("Invalid amount of function parameters")
        return (inputs[0].evaluate() as ListValue).value.fold(inputs[1].evaluate()) { acc, value -> function.evaluate(listOf(acc, value)) }
    }
}