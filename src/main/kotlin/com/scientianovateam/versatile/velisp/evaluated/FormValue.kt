package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.velisp.FormType

class FormValue(override val value: Form) : IEvaluated {
    override val type = FormType
}