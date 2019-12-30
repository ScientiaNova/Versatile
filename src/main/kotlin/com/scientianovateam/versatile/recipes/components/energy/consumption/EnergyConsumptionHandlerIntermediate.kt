package com.scientianovateam.versatile.recipes.components.energy.consumption

import com.scientianovateam.versatile.common.serialization.IJSONIntermediate
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

data class EnergyConsumptionHandlerIntermediate(val expression: IUnresolved) : IJSONIntermediate<EnergyConsumptionHandler> {
    override fun resolve(map: Map<String, IEvaluated>) = EnergyConsumptionHandler(expression.resolve(map).evaluate().value as Int)
}