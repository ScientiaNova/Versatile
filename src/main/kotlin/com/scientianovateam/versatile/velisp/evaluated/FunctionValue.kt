package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.functions.constructor.LambdaFunction
import com.scientianovateam.versatile.velisp.types.FunctionTypeHolder
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

data class FunctionValue(val parameters: List<String>, val expression: IUnresolved) : IEvaluated, IFunction {
    override val name = "anonymous function"
    override val inputCount = parameters.size..parameters.size
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        if (inputs.size != parameters.size) error("Invalid amount of inputs for function")
        val lambdaInputs = parameters.zip(inputs) { key, value -> key to value.evaluate() }.toMap()
        return expression.resolve(lambdaInputs).evaluate()
    }

    override fun <T> find(type: Class<out T>, predicate: (T) -> Boolean) = super.find(type, predicate) + expression.find(type, predicate)

    override val type = FunctionTypeHolder(parameters.size)
    override val value = expression

    override fun toJson() = FunctionCall(LambdaFunction.name, listOf(*parameters.map(::StringValue).toTypedArray(), expression)).toJson()
}