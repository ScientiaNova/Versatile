package com.scientianovateam.versatile.materialsystem.lists

import com.scientianovateam.versatile.materialsystem.main.Form

object Forms {
    private val forms = hashMapOf<String, Form>()

    @JvmStatic
    val all  get() = forms.values

    @JvmStatic
    fun add(type: Form) = forms[type.name]?.merge(type) ?: forms.put(type.name, type)

    @JvmStatic
    operator fun get(name: String): Form? = forms[name]

    @JvmStatic
    operator fun contains(name: String) = name in forms
}