package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.evaluated.function
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object FoldIndexedFunction : IFunction {
    override val name = "versatile/fold_indexed"
    override val inputCount = 3..3
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        val function = inputs[2].evaluate().function
        if (3 !in function.inputCount) error("Invalid amount of function parameters")
        return (inputs[0].evaluate() as ListValue).value.foldIndexed(inputs[1].evaluate()) { index, acc, value -> function.evaluate(listOf(NumberValue(index), acc, value)) }
    }
}