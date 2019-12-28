package com.scientianovateam.versatile.velisp.functions.mics

import com.scientianovateam.versatile.velisp.VELISPType
import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object IsFunction : IFunction {
    override val name = "versatile/is"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue(inputs.first().evaluate().type subtypes inputs.last().evaluate().value.toString().let { type ->
        VELISPType.fromName(type) ?: error("Invalid type: $type")
    })
}