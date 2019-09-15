package com.emosewapixel.pixellib.materialsystem.lists

import com.emosewapixel.pixellib.materialsystem.materials.Material

//This class contains functions for interacting with the global list of materials
object Materials {
    private val materials = hashMapOf<String, Material>()

    @JvmStatic
    val all: Collection<Material>
        get() = materials.values

    @JvmStatic
    fun add(mat: Material) = if (mat in materials.values) materials[mat.name]!!.merge(mat) else materials[mat.name] = mat

    @JvmStatic
    operator fun get(name: String) = materials[name]

    @JvmStatic
    operator fun contains(name: String) = name in materials
}