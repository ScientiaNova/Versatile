@file:JvmName("Forms")

package com.scientianova.versatile.materialsystem.lists

import com.scientianova.versatile.materialsystem.main.GlobalForm

private val forms = hashMapOf<String, GlobalForm>()

val allForms get() = forms.values

fun addForm(form: GlobalForm) = forms[form.name]?.merge(form) ?: form.also { forms[form.name] = form }

fun formFor(name: String): GlobalForm? = forms[name]

fun formExists(name: String) = name in forms