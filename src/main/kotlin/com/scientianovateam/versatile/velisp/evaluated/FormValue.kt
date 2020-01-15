package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.velisp.functions.constructor.FormFunction
import com.scientianovateam.versatile.velisp.types.FORM
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall

class FormValue(override val value: Form) : IEvaluated {
    override val type = FORM
    override fun serialize() = FunctionCall(FormFunction.name, listOf(StringValue(value.name))).serialize()
}