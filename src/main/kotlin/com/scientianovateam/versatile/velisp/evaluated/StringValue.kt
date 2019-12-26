package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.velisp.StringType

class StringValue(override val value: String) : IEvaluated {
    override val type = StringType
}