package com.scientianova.versatile.materialsystem.lists

import com.scientianova.versatile.materialsystem.main.GlobalForm

object Forms {
    private val forms = hashMapOf<String, GlobalForm>()

    @JvmStatic
    val all
        get() = forms.values

    @JvmStatic
    fun add(form: GlobalForm) = forms[form.name] ?: forms.put(form.name, form)

    @JvmStatic
    operator fun get(name: String): GlobalForm? = forms[name]

    @JvmStatic
    operator fun contains(name: String) = name in forms
}