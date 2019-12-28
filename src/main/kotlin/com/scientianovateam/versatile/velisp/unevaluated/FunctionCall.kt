package com.scientianovateam.versatile.velisp.unevaluated

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.velisp.FunctionType
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.registry.VELISP_FUNCTIONS
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

data class FunctionCall(val name: String, var inputs: List<IUnresolved>) : IUnevaluated {
    val function = VELISP_FUNCTIONS[name.toResLoc(Versatile.MOD_ID, '/')]
            ?: error("Unknown function")

    override fun dependencies(): List<String> = inputs.flatMap(IUnresolved::dependencies)

    override fun resolve(map: Map<String, IEvaluated>): FunctionCall {
        inputs = inputs.map { it.tryToResolve(map) }
        return this
    }

    override fun evaluate() = if (inputs.size in function.inputCount) function.evaluate(inputs) else error("Invalid amount of inputs for function")

    override val type = FunctionType(function.inputCount)
}