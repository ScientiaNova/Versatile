package com.scientianovateam.versatile.velisp.functions.string

import com.scientianovateam.versatile.items.ARMOR_TIERS
import com.scientianovateam.versatile.velisp.expr
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unevaluated.IUnevaluated
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

object ArmorTierExistsFunction : IFunction {
    override val name = "versatile/armor_tier_exists"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = ((inputs[0] as IUnevaluated).evaluate().value.toString() in ARMOR_TIERS).expr()
}