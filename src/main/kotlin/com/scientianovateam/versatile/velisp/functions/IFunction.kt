package com.scientianovateam.versatile.velisp.functions

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

interface IFunction {
    val inputCount: IntRange

    fun evaluate(inputs: List<IUnresolved>): IEvaluated
}