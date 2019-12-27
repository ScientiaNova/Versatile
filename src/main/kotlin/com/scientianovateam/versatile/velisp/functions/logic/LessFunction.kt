package com.scientianovateam.versatile.velisp.functions.logic

import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object LessFunction : IFunction {
    override val name = "versatile/less"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = BoolValue((inputs.first().evaluate().value as Double) < inputs.last().evaluate().value as Double)
}