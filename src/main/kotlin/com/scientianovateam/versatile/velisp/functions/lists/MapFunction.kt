package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.evaluated.function
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object MapFunction : IFunction {
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        val function = inputs.last().evaluate().function
        if (1 !in function.inputCount) throw IllegalStateException("Invalid amount of function parameters")
        return ListValue((inputs.first().evaluate() as ListValue).value.map { function.evaluate(listOf(it)) })
    }
}