package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.velisp.unevaluated.IUnevaluated

interface IEvaluated : IUnevaluated {
    override fun evaluate() = this
    val value: Any?
}