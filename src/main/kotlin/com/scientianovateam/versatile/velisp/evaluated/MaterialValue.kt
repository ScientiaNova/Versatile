package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.properties.IPropertyContainer
import com.scientianovateam.versatile.velisp.types.MaterialType
import com.scientianovateam.versatile.velisp.functions.constructor.MaterialFunction
import com.scientianovateam.versatile.velisp.types.MATERIAL_TYPE
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall

class MaterialValue(override val value: Material) : IEvaluated, IPropertyContainer {
    override val type = MATERIAL_TYPE
    override val properties = value.properties
    override fun toJson() = FunctionCall(MaterialFunction.name, listOf(StringValue(value.name))).toJson()
}