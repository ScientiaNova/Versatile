package com.scientianovateam.versatile.velisp.unevaluated

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.velisp.FunctionType
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.registry.VELISPRegistries
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

data class FunctionCall(val name: String, private var inputs: List<IUnresolved>) : IUnevaluated {
    val function = VELISPRegistries.FUNCTION_REGISTRY[name.toResLoc(Versatile.MOD_ID, '/')]
            ?: error("Unknown function")

    override fun dependencies(): List<String> = inputs.flatMap(IUnresolved::dependencies)

    override fun resolve(map: Map<String, IEvaluated>): FunctionCall {
        inputs = inputs.map { it.tryToResolve(map) }
        return this
    }

    override fun evaluate() = if (inputs.size in function.inputCount) function.evaluate(inputs) else error("Invalid amount of inputs for function")

    override val type = FunctionType(function.inputCount)
}