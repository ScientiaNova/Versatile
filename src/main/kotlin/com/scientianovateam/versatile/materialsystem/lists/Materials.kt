package com.scientianovateam.versatile.materialsystem.lists

import com.scientianovateam.versatile.materialsystem.main.Material

object Materials {
    private val materials = hashMapOf<String, Material>()

    @JvmStatic
    val all
        get() = materials.values.distinct()

    @JvmStatic
    fun add(mat: Material) {
        materials[mat.name] = mat
    }

    @JvmStatic
    operator fun get(name: String) = materials[name]

    @JvmStatic
    operator fun contains(name: String) = name in materials
}