package com.scientianovateam.versatile.velisp.types

import com.scientianovateam.versatile.common.extensions.serialized
import com.scientianovateam.versatile.common.extensions.toResLocV
import net.minecraft.util.ResourceLocation

class VELISPType(val name: ResourceLocation, val generics: Int = 0) {
    override fun toString() = name.serialized
    operator fun invoke(vararg types: ITypeHolder) = TypeHolder(this, types.toList())
}

val AnyType = VELISPType("any".toResLocV())
val ANY_TYPE = AnyType()

val NumberType = VELISPType("number".toResLocV())
val NUMBER_TYPE = NumberType()

val BoolType = VELISPType("bool".toResLocV())
val BOOL_TYPE = BoolType()

val StringType = VELISPType("string".toResLocV())
val STRING_TYPE = StringType()

val ListType = VELISPType("list".toResLocV(), 1)

val OptionalType = VELISPType("optional".toResLocV(), 1)

val FunctionType = VELISPType("function".toResLocV())

val MaterialType = VELISPType("material".toResLocV())
val MATERIAL_TYPE = MaterialType()

val FormType = VELISPType("form".toResLocV())
val FORM_TYPE = FormType()

val NothingType = VELISPType("nothing".toResLocV())
val NOTHING_TYPE = NothingType()

val JsonType = VELISPType("json".toResLocV())
val JSON_TYPE = JsonType()