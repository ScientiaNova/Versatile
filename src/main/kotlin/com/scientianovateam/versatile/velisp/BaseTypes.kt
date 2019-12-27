package com.scientianovateam.versatile.velisp

internal val TYPES = mutableSetOf<VELISPType>()

abstract class VELISPType(val name: String, private val superTypes: Set<VELISPType> = emptySet()) {
    init {
        TYPES += this
    }

    final override fun toString() = name

    open val allSuperTypes = superTypes.fold(setOf(this)) { set, type -> (set + type) union type.superTypes } + AnyType

    infix fun subtypes(other: VELISPType) = this == NothingType || other in allSuperTypes

    infix fun supertypes(other: VELISPType) = other == NothingType || this in other.allSuperTypes
}

object AnyType : VELISPType("any")

object NumberType : VELISPType("number")

object BoolType : VELISPType("bool")

object StringType : VELISPType("string")

data class ListType(val type: VELISPType) : VELISPType("list")

data class OptionalType(val type: VELISPType) : VELISPType("optional")

data class FunctionType(val inputCount: IntRange) : VELISPType("function")

object MaterialType : VELISPType("material")

object FormType : VELISPType("form")

object NothingType : VELISPType("nothing") {
    override val allSuperTypes = TYPES
}