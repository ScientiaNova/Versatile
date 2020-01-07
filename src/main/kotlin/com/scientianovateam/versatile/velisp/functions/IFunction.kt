package com.scientianovateam.versatile.velisp.functions

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.registry.toRegName
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

interface IFunction {
    val name: String
    val symbol: String? get() = null
    val inputCount: IntRange
    fun evaluate(inputs: List<IUnresolved>): IEvaluated
}

val IFunction.practicalName
    get() = symbol ?: name.toRegName().let { if (it.modId == Versatile.MOD_ID) it.name else toString() }