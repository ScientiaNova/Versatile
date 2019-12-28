package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.evaluated.function
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object ZipFunction : IFunction {
    override val name = "versatile/zip"
    override val inputCount = 3..3
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        val function = inputs[2].evaluate().function
        if (2 !in function.inputCount) error("Invalid amount of function parameters")
        return ListValue((inputs[0].evaluate() as ListValue).value.zip((inputs[1].evaluate() as ListValue).value) { first, second -> function.evaluate(listOf(first, second)) })
    }
}