package com.scientianovateam.versatile.velisp.unresolved

import com.google.gson.JsonElement
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.unevaluated.IUnevaluated

interface IUnresolved {
    fun resolve(map: Map<String, IEvaluated>): IUnevaluated
    fun tryToResolve(map: Map<String, IEvaluated>): IUnresolved = this
    fun <T> find(type: Class<out T>, predicate: (T) -> Boolean): List<T> {
        if (type.isInstance(this)) {
            val casted = type.cast(this)
            if (predicate(casted)) return listOf(casted)
        }
        return emptyList()
    }

    fun toJson(): JsonElement
}

fun IUnresolved.evaluate() = (this as? IUnevaluated)?.evaluate() ?: error("tried to evaluate unresolved value")