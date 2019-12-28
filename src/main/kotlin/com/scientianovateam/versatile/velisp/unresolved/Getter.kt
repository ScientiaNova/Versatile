package com.scientianovateam.versatile.velisp.unresolved

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated

data class Getter(val name: String) : IUnresolved {
    override fun dependencies() = listOf(name)
    override fun resolve(map: Map<String, IEvaluated>) = map[name] ?: error("Unknown variable name in getter")
    override fun tryToResolve(map: Map<String, IEvaluated>) = map[name] ?: this
}