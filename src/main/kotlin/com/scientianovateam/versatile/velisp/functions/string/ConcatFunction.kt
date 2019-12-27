package com.scientianovateam.versatile.velisp.functions.string

import com.scientianovateam.versatile.common.extensions.shorten
import com.scientianovateam.versatile.velisp.evaluated.StringValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object ConcatFunction : IFunction {
    override val inputCount = 1..Int.MAX_VALUE
    override fun evaluate(inputs: List<IUnresolved>) = StringValue(inputs.joinToString { unevaluated ->
        unevaluated.evaluate().value.let { if (it is Double) it.shorten() else it }.toString()
    })
}