package com.scientianovateam.versatile.velisp.unevaluated

import com.scientianovateam.versatile.common.extensions.toJson
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.StringValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.functions.practicalName
import com.scientianovateam.versatile.velisp.registry.VELISP_FUNCTIONS
import com.scientianovateam.versatile.velisp.registry.toRegName
import com.scientianovateam.versatile.velisp.types.FunctionTypeHolder
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

data class FunctionCall(val function: IFunction, var inputs: List<IUnresolved>) : IUnevaluated {
    constructor(name: String, inputs: List<IUnresolved>) :
            this(VELISP_FUNCTIONS[name.toRegName()] ?: error("Unknown function: $name"), inputs)

    override fun <T> find(type: Class<out T>, predicate: (T) -> Boolean) = super.find(type, predicate) + inputs.flatMap { it.find(type, predicate) }

    override fun resolve(map: Map<String, IEvaluated>): FunctionCall {
        inputs = inputs.map { it.tryToResolve(map) }
        return this
    }

    override fun evaluate() = if (inputs.size in function.inputCount) function.evaluate(inputs) else error("Invalid amount of inputs for function")

    override val type = FunctionTypeHolder(inputs.size)

    override fun serialize() = (listOf(StringValue(function.practicalName)) + inputs).map(IUnresolved::serialize).toJson()
}