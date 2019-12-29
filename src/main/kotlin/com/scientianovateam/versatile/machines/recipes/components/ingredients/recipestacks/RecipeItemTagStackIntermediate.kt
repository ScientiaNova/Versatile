package com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks

import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.serialization.IJSONIntermediate
import com.scientianovateam.versatile.machines.recipes.components.ingredients.utility.times
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import net.minecraft.tags.ItemTags

data class RecipeItemTagStackIntermediate(val nameExpr: IUnresolved, val countExpr: IUnresolved) : IJSONIntermediate<RecipeItemTagStack> {
    override fun resolve(map: Map<String, IEvaluated>) = RecipeItemTagStack(
            ItemTags.Wrapper(nameExpr.resolve(map).evaluate().value.toString().toResLoc())
                    * (countExpr.resolve(map).evaluate().value as Int)
    )
}