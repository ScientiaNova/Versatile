package com.emosewapixel.pixellib.materialsystem.lists

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.main.Material
import org.openzen.zencode.java.ZenCodeGlobals
import org.openzen.zencode.java.ZenCodeType

//This class contains functions for interacting with the global list of materials
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.lists.Materials")
object Materials {
    private val materials = hashMapOf<String, Material>()

    @ZenCodeGlobals.Global("materials")
    val instance = this

    @JvmStatic
    val all
        @ZenCodeType.Getter get() = materials.values.distinct()

    @JvmStatic
    fun add(mat: Material) {
        val merged = mat.names.mapNotNull(this::get).distinct().fold(mat) { acc, current -> current.merge(acc) }
        mat.names.forEach { materials[it] = merged }
    }

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    operator fun get(name: String) = materials[name]

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    operator fun contains(name: String) = name in materials
}