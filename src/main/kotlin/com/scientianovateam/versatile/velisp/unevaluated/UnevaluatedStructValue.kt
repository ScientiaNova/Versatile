package com.scientianovateam.versatile.velisp.unevaluated

import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.StructValue
import com.scientianovateam.versatile.velisp.types.STRUCT
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

class UnevaluatedStructValue(private var values: Map<String, IUnresolved>) : IUnevaluated {
    override fun <T> find(type: Class<out T>, predicate: (T) -> Boolean) = super.find(type, predicate) + values.values.flatMap { it.find(type, predicate) }

    override fun resolve(map: Map<String, IEvaluated>): UnevaluatedStructValue {
        values = values.mapValues { it.value.tryToResolve(map) }
        return this
    }

    override fun evaluate() = StructValue(values.mapValues { (it.value as IUnevaluated).evaluate() })

    override val type = STRUCT

    override fun serialize() = json {
        values.forEach { (key, value) ->
            key to value.serialize()
        }
    }
}