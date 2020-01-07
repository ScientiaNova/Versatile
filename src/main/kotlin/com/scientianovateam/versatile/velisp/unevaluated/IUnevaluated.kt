package com.scientianovateam.versatile.velisp.unevaluated

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.types.ITypeHolder
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

interface IUnevaluated : IUnresolved {
    override fun resolve(map: Map<String, IEvaluated>) = this
    fun evaluate(): IEvaluated
    val type: ITypeHolder
}