package com.scientianovateam.versatile.velisp.types

data class TypeHolder(override val type: VELISPType, val generics: List<ITypeHolder> = emptyList()) : ITypeHolder {
    init {
        if (type.generics != generics.size) error("$type requires ${type.generics} types, but got ${generics.size}")
    }

    override fun getMutualSupertype(other: ITypeHolder) = when {
        type == NothingType -> other
        other.type == NothingType -> this
        type == other.type && other is TypeHolder && generics.size == other.generics.size ->
            TypeHolder(type, generics.zip(other.generics, ITypeHolder::getMutualSupertype))
        else -> ANY_TYPE
    }

    override fun subtypes(other: ITypeHolder) = type == NothingType || other.type == AnyType ||
            (type == other.type && other is TypeHolder && generics.size == other.generics.size && generics.indices.all { generics[it] subtypes other.generics[it] })

    override fun supertypes(other: ITypeHolder) = type == AnyType || other.type == NothingType ||
            (type == other.type && other is TypeHolder && generics.size == other.generics.size && generics.indices.all { generics[it] supertypes other.generics[it] })

    override fun toString() = if (generics.isEmpty()) type.toString() else "$type[${generics.joinToString(transform = ITypeHolder::toString)}]"
}