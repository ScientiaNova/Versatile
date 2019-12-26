package com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.extensions.toStack
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.registries.ForgeRegistries

class RecipeFluidStack(val stack: FluidStack) : IRecipeStack<FluidStack> {
    override val count = stack.amount

    override val stacks = listOf(stack.copy())

    override fun matches(other: FluidStack) = stack.isFluidEqual(other) && count <= other.amount

    override fun matchesWithoutCount(other: FluidStack) = stack.isFluidEqual(other)

    override fun toString() = "fluid:" + stack.fluid.registryName

    override fun hashCode() = toString().hashCode()

    override fun equals(other: Any?) = other is RecipeFluidStack && other.stack.isFluidStackIdentical(stack)

    override val serializer = Serializer

    object Serializer : IRegisterableJSONSerializer<RecipeFluidStack, JsonObject> {
        override val registryName = "versatile:item_stack".toResLoc()

        override fun read(json: JsonObject): RecipeFluidStack {
            val item = json.getAsJsonPrimitive("item")?.asString?.toResLoc()?.let { ForgeRegistries.FLUIDS.getValue(it) }
                    ?: Fluids.EMPTY
            val count = if (json.has("count")) json.getAsJsonPrimitive("count").asNumber?.toInt() ?: 1 else 1
            return RecipeFluidStack(item * count)
        }

        override fun write(obj: RecipeFluidStack) = json {
            "item" to obj.stack.fluid
            if (obj.count > 1) "count" to obj.count
        }
    }

    companion object {
        @JvmField
        val EMPTY = RecipeFluidStack(FluidStack.EMPTY)
    }
}

operator fun MutableCollection<IRecipeStack<FluidStack>>.plusAssign(stack: FluidStack) = plusAssign(RecipeFluidStack(stack))
fun Fluid.toRStack(count: Int = 1000) = RecipeFluidStack(this.toStack(count))
fun FluidStack.r() = RecipeFluidStack(this)