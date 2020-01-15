package com.scientianovateam.versatile.velisp.functions.string

import com.scientianovateam.versatile.items.TOOL_TIERS
import com.scientianovateam.versatile.materialsystem.lists.ELEMENTS
import com.scientianovateam.versatile.velisp.expr
import com.scientianovateam.versatile.velisp.functions.IFunction
import com.scientianovateam.versatile.velisp.unevaluated.IUnevaluated
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

object ToolTierExistsFunction : IFunction {
    override val name = "versatile/tool_tier_exists"
    override val inputCount = 1..1
    override fun evaluate(inputs: List<IUnresolved>) = ((inputs[0] as IUnevaluated).evaluate().value.toString() in TOOL_TIERS).expr()
}