package com.scientianovateam.versatile.velisp.functions.constructor

import com.scientianovateam.versatile.velisp.evaluated.SomeValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object SomeFunction : IFunction {
    override val name = "versatile/some"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = SomeValue(inputs[0].evaluate())
}