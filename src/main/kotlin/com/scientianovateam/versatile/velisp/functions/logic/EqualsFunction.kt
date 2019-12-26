package com.scientianovateam.versatile.velisp.functions.logic

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

object EqualsFunction : IFunction {
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue((inputs[0] as IEvaluated).evaluate().value.toString() == (inputs[1] as IEvaluated).evaluate().value.toString())
}