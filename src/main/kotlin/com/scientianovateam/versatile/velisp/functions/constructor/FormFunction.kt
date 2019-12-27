package com.scientianovateam.versatile.velisp.functions.constructor

import com.scientianovateam.versatile.materialsystem.lists.Forms
import com.scientianovateam.versatile.velisp.evaluated.FormValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate

object FormFunction : IFunction {
    override val name = "versatile/form"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = FormValue(inputs.first().evaluate().value.toString().let {
        Forms[it] ?: error("No material with name $it")
    })
}