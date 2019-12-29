package com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks

import com.scientianovateam.versatile.common.serialization.IJSONIntermediate
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

data class ChancedRecipeStackIntermediate<T>(val stackIntermediate: IJSONIntermediate<out IRecipeStack<T>>, val chanceExpression: IUnresolved) : IJSONIntermediate<ChancedRecipeStack<T>> {
    override fun resolve(map: Map<String, IEvaluated>) = ChancedRecipeStack(stackIntermediate.resolve(map), chanceExpression.resolve(map).evaluate().value as Float)
}