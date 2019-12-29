package com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks

import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.serialization.IJSONIntermediate
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.evaluated.StringValue
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import net.minecraft.item.Items
import net.minecraftforge.registries.ForgeRegistries

data class RecipeItemStackIntermediate(val nameExpr: IUnresolved, val countExpr: IUnresolved) : IJSONIntermediate<RecipeItemStack> {
    override fun resolve(map: Map<String, IEvaluated>) = RecipeItemStack(
            (ForgeRegistries.ITEMS.getValue(nameExpr.resolve(map).evaluate().value.toString().toResLoc())
                    ?: Items.AIR) * (countExpr.resolve(map).evaluate().value as Int)
    )

    companion object {
        val EMPTY = RecipeItemStackIntermediate(StringValue("minecraft:air"), NumberValue(1))
    }
}