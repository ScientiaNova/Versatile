package com.scientianovateam.versatile.velisp.functions.constructor

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object RangeFunction : IFunction {
    override val name = "versatile/range"
    override val inputCount = 2..3
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        val start = inputs[0].evaluate().value as Double
        val end = inputs[1].evaluate().value as Double
        val step = inputs.getOrElse(3) { 1 } as Double
        return ListValue(generateSequence(start) { prev ->
            val next = prev + step
            if (next > end) null else next
        }.map(::NumberValue).toList())
    }
}