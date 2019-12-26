package com.scientianovateam.versatile.velisp.functions.arithemetic

import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unevaluated.IUnevaluated
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

object AddFunction : IFunction {
    override val inputCount = 2..Int.MAX_VALUE
    override fun evaluate(inputs: List<IUnresolved>) = NumberValue(inputs.map { (it as IUnevaluated).evaluate().value as Double }.sum())
}