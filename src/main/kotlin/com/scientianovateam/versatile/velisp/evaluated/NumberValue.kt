package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.velisp.NumberType

class NumberValue(override val value: Double) : IEvaluated {
    override val type = NumberType
}