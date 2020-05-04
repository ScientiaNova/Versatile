package com.scientianova.versatile.materialsystem.lists

import com.scientianova.versatile.materialsystem.main.Material

object Materials {
    private val materials = hashMapOf<String, Material>()

    @JvmStatic
    val all
        get() = materials.values.distinct()

    @JvmStatic
    fun add(mat: Material) {
        val merged = mat.names.mapNotNull(this::get).distinct().fold(mat) { acc, current -> current.merge(acc) }
        mat.names.forEach { materials[it] = merged }
    }

    @JvmStatic
    operator fun get(name: String) = materials[name]

    @JvmStatic
    operator fun contains(name: String) = name in materials
}