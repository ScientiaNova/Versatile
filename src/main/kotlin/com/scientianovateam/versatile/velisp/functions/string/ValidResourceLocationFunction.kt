package com.scientianovateam.versatile.velisp.functions.string

import com.scientianovateam.versatile.velisp.expr
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate
import net.minecraft.util.ResourceLocation

object ValidResourceLocationFunction : IFunction {
    override val name = "versatile/valid_resource_location"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = try {
        ResourceLocation(inputs[0].evaluate().value.toString())
        true
    } catch (e: Throwable) {
        false
    }.expr()
}