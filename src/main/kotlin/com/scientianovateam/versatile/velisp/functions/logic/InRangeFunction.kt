package com.scientianovateam.versatile.velisp.functions.logic

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object InRangeFunction : IFunction {
    override val name = "versatile/in_range"
    override val inputCount = 3..3
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue(inputs[0].evaluate().value as Double in inputs[1].evaluate().value as Double..inputs[2].evaluate().value as Double)
}