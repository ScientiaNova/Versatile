package com.scientianovateam.versatile.recipes.components.ingredients.fluids.output

import com.scientianovateam.versatile.common.serialization.IJSONIntermediate
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import net.minecraftforge.fluids.FluidStack

data class FluidOutputsHandlerIntermediate(val stackIntermediates: List<IJSONIntermediate<ChancedRecipeStack<FluidStack>>>) : IJSONIntermediate<FluidOutputsHandler> {
    override fun resolve(map: Map<String, IEvaluated>) = FluidOutputsHandler(stackIntermediates.map { it.resolve(map) })
}