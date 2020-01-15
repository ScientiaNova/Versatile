package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.velisp.functions.constructor.MaterialStackFunction
import com.scientianovateam.versatile.velisp.types.MATERIAL_STACK
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall

class MaterialStackValue(val material: MaterialValue, val count: NumberValue) : IEvaluated {
    override val type = MATERIAL_STACK
    override val value = material.value * count.value.toInt()

    override fun serialize() = FunctionCall(MaterialStackFunction, listOf(material, count)).serialize()
}