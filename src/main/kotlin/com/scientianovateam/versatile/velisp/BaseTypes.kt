package com.scientianovateam.versatile.velisp

abstract class VELISPType(val name: String) {
    final override fun toString() = name
}

object NumberType : VELISPType("number")

object BoolType : VELISPType("bool")

object StringType : VELISPType("string")

data class ListType(val type: VELISPType?) : VELISPType("list")

data class OptionalType(val type: VELISPType) : VELISPType("optional")

data class FunctionType(val inputCount: IntRange) : VELISPType("function")

object MaterialType : VELISPType("material")

object FormType : VELISPType("form")