package com.scientianova.versatile.machines.recipes.components.ingredients.recipestacks

import com.scientianova.versatile.common.extensions.times
import com.scientianova.versatile.machines.recipes.components.ingredients.utility.TagStack
import com.scientianova.versatile.machines.recipes.components.ingredients.utility.toStack
import net.minecraft.fluid.Fluid
import net.minecraft.tags.Tag
import net.minecraftforge.fluids.FluidStack

class RecipeFluidTagStack(stack: TagStack<Fluid>) : IRecipeStack<FluidStack> {
    val tag = stack.tag

    override val count = stack.count

    override val stacks get() = tag.allElements.map { it * count }

    override fun matches(other: FluidStack) = count <= other.amount && other.fluid in tag

    override fun matchesWithoutCount(other: FluidStack) = other.fluid in tag

    override fun toString() = "fluid_tag:" + tag.id

    override fun hashCode() = toString().hashCode()

    override fun equals(other: Any?) = other is RecipeFluidTagStack && other.tag.id == tag.id && other.count == count
}

operator fun MutableCollection<IRecipeStack<FluidStack>>.plusAssign(stack: TagStack<Fluid>) = plusAssign(RecipeFluidTagStack(stack))
fun Tag<Fluid>.toRStack(count: Int = 1000) = RecipeFluidTagStack(this.toStack(count))
fun TagStack<Fluid>.r() = RecipeFluidTagStack(this)