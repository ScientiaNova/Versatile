package com.scientianovateam.versatile.machines.recipes.components.ingredients.items

import com.scientianovateam.versatile.common.serialization.IJSONIntermediate
import com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import net.minecraft.item.ItemStack

data class ItemOutputsHandlerIntermediate(val stackIntermediates: List<IJSONIntermediate<ChancedRecipeStack<ItemStack>>>) : IJSONIntermediate<ItemOutputsHandler> {
    override fun resolve(map: Map<String, IEvaluated>) = ItemOutputsHandler(stackIntermediates.map { it.resolve(map) })
}