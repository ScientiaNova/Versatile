package com.scientianovateam.versatile.velisp.functions.constructor

import com.scientianovateam.versatile.velisp.evaluated.MaterialStackValue
import com.scientianovateam.versatile.velisp.evaluated.MaterialValue
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

object MaterialStackFunction : IFunction {
    override val name = "versatile/stack"
    override val inputCount = 1..2
    override fun evaluate(inputs: List<IUnresolved>) = MaterialStackValue(inputs.first() as MaterialValue, inputs.getOrNull(1) as? NumberValue
            ?: NumberValue.ONE)
}