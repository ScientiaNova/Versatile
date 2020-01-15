package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.common.extensions.toJson
import com.scientianovateam.versatile.velisp.functions.constructor.ListFunction
import com.scientianovateam.versatile.velisp.types.ITypeHolder
import com.scientianovateam.versatile.velisp.types.ListType
import com.scientianovateam.versatile.velisp.types.NothingType
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall

class ListValue(override val value: List<IEvaluated>) : IEvaluated {
    override val type = ListType(if (value.isEmpty()) NothingType() else value.map(IEvaluated::type).reduce(ITypeHolder::getMutualSupertype))

    override fun serialize() = FunctionCall(ListFunction.name, value).serialize()

    override fun toJSON() = value.mapNotNull(IEvaluated::toJSON).toJson()
}