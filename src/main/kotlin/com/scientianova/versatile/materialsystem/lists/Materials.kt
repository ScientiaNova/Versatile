package com.scientianova.versatile.materialsystem.lists

import com.scientianova.versatile.materialsystem.main.Material

object Materials {
    private val materials = hashMapOf<String, Material>()

    @JvmStatic
    val all
        get() = materials.values.distinct()

    @JvmStatic
    fun add(mat: Material) {
        val merged = mat.associatedNames.mapNotNull(this::get).distinct().fold(mat, Material::merge)
        mat.associatedNames.forEach { materials[it] = merged }
    }

    @JvmStatic
    operator fun get(name: String) = materials[name]

    @JvmStatic
    operator fun contains(name: String) = name in materials
}