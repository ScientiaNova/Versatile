package com.scientianovateam.versatile.velisp.functions.logic

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.NullValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object MatchFunction : IFunction {
    override val name = "versatile/match"
    override val inputCount = 3..Int.MAX_VALUE
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        val value = inputs[0].evaluate()
        val list: List<Pair<IUnresolved, IUnresolved>>
        val default: IUnresolved
        if (inputs.size % 2 == 0) {
            list = inputs.drop(1).zipWithNext().filterIndexed { index, _ -> index % 2 == 0 }
            default = NullValue
        } else {
            list = inputs.drop(1).dropLast(1).zipWithNext().filterIndexed { index, _ -> index % 2 == 0 }
            default = inputs.last()
        }
        return list.firstOrNull { value == it.first.evaluate() }?.second?.evaluate() ?: default.evaluate()
    }
}