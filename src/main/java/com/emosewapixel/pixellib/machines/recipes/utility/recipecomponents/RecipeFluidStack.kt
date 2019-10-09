package com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents

import net.minecraftforge.fluids.FluidStack

class RecipeFluidStack(val stack: FluidStack) : IRecipeStack<FluidStack> {
    override val count = stack.amount

    override val stacks = listOf(stack)

    override fun matches(other: FluidStack) = stack.isFluidEqual(other) && count <= other.amount

    companion object {
        @JvmField
        val EMPTY = RecipeFluidStack(FluidStack.EMPTY)
    }
}

operator fun MutableCollection<IRecipeStack<FluidStack>>.plusAssign(stack: FluidStack) = plusAssign(RecipeFluidStack(stack))