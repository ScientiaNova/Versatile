package com.scientianovateam.versatile.velisp.functions.mics

import com.scientianovateam.versatile.velisp.evaluated.StringValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object TypeOfFunction : IFunction {
    override val name = "versatile/type_of"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = StringValue(inputs[0].evaluate().type.toString())
}