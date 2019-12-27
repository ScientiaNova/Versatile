package com.scientianovateam.versatile.velisp.functions.logic

import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object IfFunction : IFunction {
    override val name = "versatile/if"
    override val inputCount = 3..3
    override fun evaluate(inputs: List<IUnresolved>) = if (inputs[0].evaluate().value == true) inputs[2].evaluate() else inputs[3].evaluate()
}