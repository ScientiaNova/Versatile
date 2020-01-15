package com.scientianovateam.versatile.velisp.functions.arithemetic

import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object RemainderFunction : IFunction {
    override val name = "versatile/remainder"
    override val symbol = "%"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = NumberValue(inputs[0].evaluate().value as Double % inputs[1].evaluate().value as Double)
}