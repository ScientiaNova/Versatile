package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.velisp.ListType
import com.scientianovateam.versatile.velisp.NothingType
import com.scientianovateam.versatile.velisp.functions.constructor.ListFunction
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall

class ListValue(override val value: List<IEvaluated>) : IEvaluated {
    override val type = ListType(value.map { it.type.allSuperTypes }.reduce { acc, set -> acc intersect set }.firstOrNull()
            ?: NothingType)

    override fun toJson() = FunctionCall(ListFunction.name, value).toJson()
}