package com.scientianovateam.versatile.velisp.functions.constructor

import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object ListFunction : IFunction {
    override val name = "versatile/list"
    override val inputCount = 0..Int.MAX_VALUE
    override fun evaluate(inputs: List<IUnresolved>) = ListValue(inputs.map { it.evaluate() })
}