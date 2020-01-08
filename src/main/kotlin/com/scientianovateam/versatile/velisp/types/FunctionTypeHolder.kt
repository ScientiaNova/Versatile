package com.scientianovateam.versatile.velisp.types

data class FunctionTypeHolder(val inputs: Int) : ITypeHolder {
    override val type = FunctionType
    override fun getMutualSupertype(other: ITypeHolder) = when {
        other.type == NothingType -> this
        other is FunctionTypeHolder && inputs == other.inputs -> this
        else -> ANY
    }

    override fun subtypes(other: ITypeHolder) = other.type == NothingType || (other is FunctionTypeHolder && inputs == other.inputs)

    override fun supertypes(other: ITypeHolder) = other.type == AnyType || (other is FunctionTypeHolder && inputs == other.inputs)

    override fun toString() = "function[$inputs]"
}