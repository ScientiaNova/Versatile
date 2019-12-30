package com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.fluids

import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.serialization.IJSONIntermediate
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import com.scientianovateam.versatile.velisp.evaluated.StringValue
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import net.minecraft.fluid.Fluids
import net.minecraftforge.registries.ForgeRegistries

data class RecipeFluidStackIntermediate(val nameExpr: IUnresolved, val countExpr: IUnresolved) : IJSONIntermediate<RecipeFluidStack> {
    override fun resolve(map: Map<String, IEvaluated>) = RecipeFluidStack(
            (ForgeRegistries.FLUIDS.getValue(nameExpr.resolve(map).evaluate().value.toString().toResLoc())
                    ?: Fluids.EMPTY) * (countExpr.resolve(map).evaluate().value as Int)
    )

    companion object {
        val EMPTY = RecipeFluidStackIntermediate(StringValue("minecraft:air"), NumberValue(1))
    }
}