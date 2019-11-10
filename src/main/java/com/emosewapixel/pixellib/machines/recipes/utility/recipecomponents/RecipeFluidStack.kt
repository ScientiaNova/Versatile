package com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents

import com.emosewapixel.pixellib.extensions.toStack
import net.minecraft.fluid.Fluid
import net.minecraftforge.fluids.FluidStack

class RecipeFluidStack(val stack: FluidStack) : IRecipeStack<FluidStack> {
    override val count = stack.amount

    override val stacks = listOf(stack)

    override fun matches(other: FluidStack) = stack.isFluidEqual(other) && count <= other.amount

    override fun toString() = "fluid:" + stack.fluid.registryName

    companion object {
        @JvmField
        val EMPTY = RecipeFluidStack(FluidStack.EMPTY)
    }
}

operator fun MutableCollection<IRecipeStack<FluidStack>>.plusAssign(stack: FluidStack) = plusAssign(RecipeFluidStack(stack))
fun Fluid.toRStack(count: Int = 1000) = RecipeFluidStack(this.toStack(count))
fun FluidStack.r() = RecipeFluidStack(this)