package com.scientianovateam.versatile.velisp.functions.string

import com.scientianovateam.versatile.velisp.evaluated.StringValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object ReplaceFunction : IFunction {
    override val name = "versatile/replace"
    override val inputCount = 3..3
    override fun evaluate(inputs: List<IUnresolved>) = StringValue(inputs[0].evaluate().value.toString().replace(inputs[1].evaluate().value.toString(), inputs[2].evaluate().value.toString()))
}