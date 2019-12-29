package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.*
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object MaxElementByFunction : IFunction {
    override val name = "versatile/max_element_by"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        val function = inputs.last().evaluate().function
        if (1 !in function.inputCount) error("Invalid amount of function parameters")
        return (inputs.first().evaluate() as ListValue).value.maxBy { function.evaluate(listOf(it)).value as Double }?.let(::SomeValue)
                ?: NullValue
    }
}