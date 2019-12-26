package com.scientianovateam.versatile.materialsystem.lists

import com.scientianovateam.versatile.materialsystem.main.Form

object Forms {
    private val objTypes = hashMapOf<String, Form>()

    @JvmStatic
    val all  get() = objTypes.values

    @JvmStatic
    fun add(type: Form) = objTypes[type.name]?.merge(type) ?: objTypes.put(type.name, type)

    @JvmStatic
    operator fun get(name: String): Form? = objTypes[name]

    @JvmStatic
    operator fun contains(name: String) = name in objTypes
}