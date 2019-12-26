package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.velisp.ListType

class ListValue(override val value: List<IEvaluated>) : IEvaluated {
    override val type = ListType(value.firstOrNull()?.type)
}