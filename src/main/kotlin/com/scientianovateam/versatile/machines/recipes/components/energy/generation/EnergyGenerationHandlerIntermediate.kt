package com.scientianovateam.versatile.machines.recipes.components.energy.generation

import com.scientianovateam.versatile.common.serialization.IJSONIntermediate
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

data class EnergyGenerationHandlerIntermediate(val expression: IUnresolved) : IJSONIntermediate<EnergyGenerationHandler> {
    override fun resolve(map: Map<String, IEvaluated>) = EnergyGenerationHandler(expression.resolve(map).evaluate().value as Int)
}