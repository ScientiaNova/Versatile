package com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents

import com.emosewapixel.pixellib.machines.recipes.utility.TagStack
import net.minecraft.fluid.Fluid
import net.minecraftforge.fluids.FluidStack

class RecipeFluidTagStack(stack: TagStack<Fluid>) : IRecipeStack<FluidStack> {
    val tag = stack.tag

    override val count = stack.count

    override val stacks
        get() = tag.allElements.map { FluidStack(it, count) }

    override fun matches(other: FluidStack) = count <= other.amount && other.fluid in tag
}

operator fun MutableCollection<IRecipeStack<FluidStack>>.plusAssign(stack: TagStack<Fluid>) = plusAssign(RecipeFluidTagStack(stack))