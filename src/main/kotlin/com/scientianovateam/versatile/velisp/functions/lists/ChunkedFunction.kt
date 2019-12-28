package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object ChunkedFunction : IFunction {
    override val name = "versatile/chunked"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = ListValue((inputs.first().evaluate() as ListValue).value.chunked(inputs.last().evaluate().value as Int).map(::ListValue))
}