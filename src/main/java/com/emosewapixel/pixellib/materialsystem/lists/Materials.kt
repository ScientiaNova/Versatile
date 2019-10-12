package com.emosewapixel.pixellib.materialsystem.lists

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.materials.Material
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
    val all: Collection<Material>
        @ZenCodeType.Getter get() = materials.values

    @JvmStatic
    fun add(mat: Material) = if (mat in materials.values) materials[mat.name]!!.merge(mat) else materials[mat.name] = mat

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    operator fun get(name: String) = materials[name]

    @JvmStatic
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    operator fun contains(name: String) = name in materials
}