package com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks

import com.scientianovateam.versatile.common.extensions.toStack
import net.minecraft.fluid.Fluid
import net.minecraftforge.fluids.FluidStack

class RecipeFluidStack(val stack: FluidStack) : IRecipeStack<FluidStack> {
    override val count = stack.amount

    override val stacks = listOf(stack.copy())

    override fun matches(other: FluidStack) = stack.isFluidEqual(other) && count <= other.amount

    override fun matchesWithoutCount(other: FluidStack) = stack.isFluidEqual(other)

    override fun toString() = "fluid:" + stack.fluid.registryName

    override fun hashCode() = toString().hashCode()

    override fun equals(other: Any?) = other is RecipeFluidStack && other.stack.isFluidStackIdentical(stack)

    companion object {
        @JvmField
        val EMPTY = RecipeFluidStack(FluidStack.EMPTY)
    }
}

operator fun MutableCollection<IRecipeStack<FluidStack>>.plusAssign(stack: FluidStack) = plusAssign(RecipeFluidStack(stack))
fun Fluid.toRStack(count: Int = 1000) = RecipeFluidStack(this.toStack(count))
fun FluidStack.r() = RecipeFluidStack(this)