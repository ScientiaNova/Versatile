package com.scientianovateam.versatile.velisp.functions.mics

import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object TryFunction : IFunction {
    override val name = "versatile/try"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = try {
        inputs[0].evaluate()
    } catch (e: Exception) {
        inputs[1].evaluate()
    }
}