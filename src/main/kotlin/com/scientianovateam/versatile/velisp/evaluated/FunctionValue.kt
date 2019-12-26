package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.velisp.FunctionType
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unevaluated.IUnevaluated
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

data class FunctionValue(val parameters: List<String>, val expression: IUnresolved) : IEvaluated, IFunction {
    override val inputCount = parameters.size..parameters.size
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        if (inputs.size != parameters.size) throw IllegalStateException("Invalid amount of inputs for function")
        val lambdaInputs = parameters.zip(inputs) { key, value -> key to ((value as? IUnevaluated)?.evaluate() ?: throw IllegalStateException("Function inputs were not resolved"))}.toMap()
        return expression.resolve(lambdaInputs).evaluate()
    }
    override val type = FunctionType(parameters.size..parameters.size)
    override val value = expression
}