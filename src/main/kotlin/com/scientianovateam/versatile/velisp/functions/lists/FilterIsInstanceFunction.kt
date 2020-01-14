package com.scientianovateam.versatile.velisp.functions.lists

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.types.ITypeHolder
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object FilterIsInstanceFunction : IFunction {
    override val name = "versatile/filter_is_instance"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>): IEvaluated {
        val type = inputs.last().evaluate().value.toString().let {
            ITypeHolder.fromName(it)
        }
        return ListValue((inputs[0].evaluate() as ListValue).value.filter { it.type subtypes type })
    }
}