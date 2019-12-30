package com.scientianovateam.versatile.recipes.components.ingredients.fluids.input

import com.scientianovateam.versatile.common.serialization.IJSONIntermediate
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import net.minecraftforge.fluids.FluidStack

data class FluidInputsHandlerIntermediate(val stackIntermediates: List<IJSONIntermediate<ChancedRecipeStack<FluidStack>>>) : IJSONIntermediate<FluidInputsHandler> {
    override fun resolve(map: Map<String, IEvaluated>) = FluidInputsHandler(stackIntermediates.map { it.resolve(map) })
}