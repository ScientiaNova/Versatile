package com.scientianovateam.versatile.velisp

import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.velisp.registry.VELISP_TYPES

internal val ALL_TYPES = mutableSetOf<VELISPType>()

abstract class VELISPType(val name: String, private val superTypes: Set<VELISPType> = emptySet()) {
    init {
        ALL_TYPES += this
    }

    override fun toString() = name

    open val allSuperTypes = superTypes.fold(setOf(this)) { set, type -> (set + type) union type.superTypes } + AnyType

    infix fun subtypes(other: VELISPType) = this == NothingType || other in allSuperTypes

    infix fun supertypes(other: VELISPType) = other == NothingType || this in other.allSuperTypes

    companion object {
        fun fromName(typeString: String): VELISPType? {
            val (name, info) = separateNameAndInfo(typeString)
            return VELISP_TYPES[name.toResLocV()]?.invoke(info)
        }

        fun separateNameAndInfo(type: String): Pair<String, String> {
            val name = type.takeWhile { it != '[' }
            val info = type.removePrefix(name).let { if (it.length < 2) it else it.substring(1 until it.lastIndex) }
            return name to info
        }
    }
}

object AnyType : VELISPType("versatile:any")

object NumberType : VELISPType("versatile:number")

object BoolType : VELISPType("versatile:bool")

object StringType : VELISPType("versatile:string")

data class ListType(val type: VELISPType) : VELISPType("versatile:list") {
    override fun toString() = "versatile:list[$type]"
    override val allSuperTypes = type.allSuperTypes.map(::ListType).toSet() + AnyType
}

data class OptionalType(val type: VELISPType) : VELISPType("versatile:optional") {
    override fun toString() = "versatile:optional[$type]"
    override val allSuperTypes = type.allSuperTypes.map(::OptionalType).toSet() + AnyType
}

data class FunctionType(val inputCount: IntRange) : VELISPType("versatile:function") {
    constructor(inputCount: Int) : this(inputCount..inputCount)

    override fun toString() = "versatile:function[$inputCount]"
}

object MaterialType : VELISPType("versatile:material")

object FormType : VELISPType("versatile:form")

object NothingType : VELISPType("versatile:nothing") {
    override val allSuperTypes = ALL_TYPES
}