package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.evaluated.function
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object NoneFunction : IFunction {
    override val name = "versatile/none"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        val function = inputs.last().evaluate().function
        if (1 !in function.inputCount) error("Invalid amount of function parameters")
        return BoolValue((inputs.first().evaluate() as ListValue).value.none { function.evaluate(listOf(it)) == BoolValue.TRUE })
    }
}