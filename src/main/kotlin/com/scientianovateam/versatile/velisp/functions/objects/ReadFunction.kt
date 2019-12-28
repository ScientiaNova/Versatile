package com.scientianovateam.versatile.velisp.functions.objects

import com.scientianovateam.versatile.materialsystem.properties.IPropertyContainer
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object ReadFunction : IFunction {
    // TODO Variant with optional?
    override val name = "versatile/read"
    override val inputCount = 2..2
    override fun evaluate(inputs: List<IUnresolved>) = (inputs.last() as IPropertyContainer)
            .properties[inputs.first().evaluate().value as String]
            ?: error("Property ${inputs.first()} not found")
}