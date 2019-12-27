package com.scientianovateam.versatile.velisp.functions.optional

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.NullValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object IsPresentFunction : IFunction {
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = if (inputs.first().evaluate() is NullValue) BoolValue.TRUE else BoolValue.FALSE
}