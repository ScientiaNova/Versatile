package com.scientianovateam.versatile.velisp.functions.constructor

import com.scientianovateam.versatile.materialsystem.lists.Materials
import com.scientianovateam.versatile.velisp.evaluated.MaterialValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object MaterialFunction : IFunction {
    override val name = "versatile/material"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = MaterialValue(inputs.first().evaluate().value.toString().let {
        Materials[it] ?: error("No material with name $it")
    })
}