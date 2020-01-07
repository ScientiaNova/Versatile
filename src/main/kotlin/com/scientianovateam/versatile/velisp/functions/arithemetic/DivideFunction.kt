package com.scientianovateam.versatile.velisp.functions.arithemetic

import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object DivideFunction : IFunction {
    override val name = "versatile/divide"
    override val symbol = "/"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = NumberValue(inputs.first().evaluate().value as Double / inputs.last().evaluate().value as Double)
}