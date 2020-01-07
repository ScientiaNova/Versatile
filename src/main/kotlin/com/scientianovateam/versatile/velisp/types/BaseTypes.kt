package com.scientianovateam.versatile.velisp.types

class VELISPType(val name: String, val generics: Int = 0) {
    override fun toString() = name
    operator fun invoke(vararg types: ITypeHolder) = TypeHolder(this, types.toList())
}

val AnyType = VELISPType("versatile:any")
val ANY_TYPE = AnyType()

val NumberType = VELISPType("versatile:number")
val NUMBER_TYPE = NumberType()

val BoolType = VELISPType("versatile:bool")
val BOOL_TYPE = BoolType()

val StringType = VELISPType("versatile:string")
val STRING_TYPE = StringType()

val ListType = VELISPType("versatile:list", 1)

val OptionalType = VELISPType("versatile:optional", 1)

val FunctionType = VELISPType("versatile:function")

val MaterialType = VELISPType("versatile:material")
val MATERIAL_TYPE = MaterialType()

val FormType = VELISPType("versatile:form")
val FORM_TYPE = FormType()

val NothingType = VELISPType("versatile:nothing")
val NOTHING_TYPE = NothingType()