package com.scientianovateam.versatile.recipes.components.time

import com.scientianovateam.versatile.common.serialization.IJSONIntermediate
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

data class TimeHandlerIntermediate(val expression: IUnresolved) : IJSONIntermediate<TimeHandler> {
    override fun resolve(map: Map<String, IEvaluated>) = TimeHandler(expression.resolve(map).evaluate().value as Int)
}