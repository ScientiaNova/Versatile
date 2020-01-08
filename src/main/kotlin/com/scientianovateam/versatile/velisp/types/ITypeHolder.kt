package com.scientianovateam.versatile.velisp.types

import com.scientianovateam.versatile.velisp.registry.VELISP_TYPES

interface ITypeHolder {
    val type: VELISPType
    fun getMutualSupertype(other: ITypeHolder): ITypeHolder
    infix fun supertypes(other: ITypeHolder): Boolean
    infix fun subtypes(other: ITypeHolder): Boolean

    companion object {
        fun fromName(name: String): ITypeHolder {
            val mainName = name.takeWhile { it != '[' }
            return if (mainName == "function") FunctionTypeHolder(name.removePrefix("function[").dropLast(1).toInt())
            else {
                val mainType = VELISP_TYPES[mainName] ?: error("No such type $mainName")
                val types = name.removePrefix(mainName)
                if (types.isEmpty()) mainType()
                else mainType(*types.drop(1).dropLast(1).split(", ").map(Companion::fromName).toTypedArray())
            }
        }
    }
}