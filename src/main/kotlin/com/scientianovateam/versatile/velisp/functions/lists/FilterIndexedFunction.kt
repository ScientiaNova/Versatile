package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.*
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object FilterIndexedFunction : IFunction {
    override val name = "versatile/filter_indexed"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        val function = inputs.last().evaluate().function
        if (2 !in function.inputCount) error("Invalid amount of function parameters")
        return ListValue((inputs.first().evaluate() as ListValue).value.filterIndexed { index, value ->
            function.evaluate(listOf(NumberValue(index), value)) == BoolValue.TRUE
        })
    }
}