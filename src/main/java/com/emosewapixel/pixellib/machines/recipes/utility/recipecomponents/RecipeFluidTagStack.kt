package com.emosewapixel.pixellib.machines.recipes.utility.recipecomponents

import com.emosewapixel.pixellib.extensions.times
import com.emosewapixel.pixellib.machines.recipes.utility.TagStack
import com.emosewapixel.pixellib.machines.recipes.utility.toStack
import net.minecraft.fluid.Fluid
import net.minecraft.tags.Tag
import net.minecraftforge.fluids.FluidStack

class RecipeFluidTagStack(stack: TagStack<Fluid>) : IRecipeStack<FluidStack> {
    val tag = stack.tag

    override val count = stack.count

    override val stacks
        get() = tag.allElements.map { it * count }

    override fun matches(other: FluidStack) = count <= other.amount && other.fluid in tag

    override fun toString() = "fluid_tag:" + tag.id
}

operator fun MutableCollection<IRecipeStack<FluidStack>>.plusAssign(stack: TagStack<Fluid>) = plusAssign(RecipeFluidTagStack(stack))
fun Tag<Fluid>.toRStack(count: Int = 1000) = RecipeFluidTagStack(this.toStack(count))
fun TagStack<Fluid>.r() = RecipeFluidTagStack(this)