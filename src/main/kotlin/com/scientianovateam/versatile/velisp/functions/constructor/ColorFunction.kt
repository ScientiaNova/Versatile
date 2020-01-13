package com.scientianovateam.versatile.velisp.functions.constructor

import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

object ColorFunction : IFunction {
    override val name = "versatile/color"
    override val inputCount = 3..4
    override fun evaluate(inputs: List<IUnresolved>) = NumberValue(
            ((inputs.getOrNull(3) as? NumberValue ?: NumberValue.BYTE_LIMIT).value.toInt() and 0xFF shl 24) or
                    ((inputs[0] as NumberValue).value.toInt() and 0xFF shl 16) or
                    ((inputs[1] as NumberValue).value.toInt() and 0xFF shl 8) or
                    ((inputs[2] as NumberValue).value.toInt() and 0xFF)
    )
}