package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.velisp.BoolType

class BoolValue(override val value: Boolean) : IEvaluated {
    override val type = BoolType

    companion object {
        val TRUE = BoolValue(true)
        val FALSE = BoolValue(false)
    }
}