package com.scientianovateam.versatile.velisp.unevaluated

import com.scientianovateam.versatile.common.extensions.toJson
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.velisp.FunctionType
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.StringValue
import com.scientianovateam.versatile.velisp.registry.VELISP_FUNCTIONS
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

data class FunctionCall(val name: String, var inputs: List<IUnresolved>) : IUnevaluated {
    val function = VELISP_FUNCTIONS[name.toResLocV('/')]
            ?: error("Unknown function")

    override fun <T> find(type: Class<out T>, predicate: (T) -> Boolean) = super.find(type, predicate) + inputs.flatMap { it.find(type, predicate) }

    override fun resolve(map: Map<String, IEvaluated>): FunctionCall {
        inputs = inputs.map { it.tryToResolve(map) }
        return this
    }

    override fun evaluate() = if (inputs.size in function.inputCount) function.evaluate(inputs) else error("Invalid amount of inputs for function")

    override val type = FunctionType(function.inputCount)

    override fun toJson() = (listOf(StringValue(function.name)) + inputs).map(IUnresolved::toJson).toJson()
}