package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.velisp.registry.VELISP_FUNCTIONS
import com.scientianovateam.versatile.velisp.unevaluated.IUnevaluated
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

interface IEvaluated : IUnevaluated {
    override fun evaluate() = this
    val value: Any
}

val IEvaluated.function
    get() = when (this) {
        is FunctionValue -> this
        is StringValue -> VELISP_FUNCTIONS[this.value.toResLocV('/')] ?: error("No such function with name $value")
        else -> error("Invalid function")
    }

fun IEvaluated.callFunction(vararg inputs: IUnresolved) = function.evaluate(inputs.toList())