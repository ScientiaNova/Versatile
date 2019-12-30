package com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.fluids

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.common.serialization.IRegisterableSerializer
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.IRecipeStack
import com.scientianovateam.versatile.velisp.convertToExpression
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import net.minecraft.fluid.Fluid
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fluids.FluidStack

class RecipeFluidStack(val stack: FluidStack) : IRecipeStack<FluidStack> {
    override val count = stack.amount

    override val stacks = listOf(stack.copy())

    override val singleStack = stack.copy()

    override fun matches(other: FluidStack) = stack.isFluidEqual(other) && count <= other.amount

    override fun matchesWithoutCount(other: FluidStack) = stack.isFluidEqual(other)

    override fun toString() = "fluid:" + stack.fluid.registryName

    override fun hashCode() = toString().hashCode()

    override fun equals(other: Any?) = other is RecipeFluidStack && other.stack.isFluidStackIdentical(stack)

    override val serializer = Serializer

    object Serializer : IRegisterableSerializer<RecipeFluidStack, JsonObject> {
        override val registryName = "versatile:item_stack".toResLoc()

        override fun read(json: JsonObject): RecipeFluidStackIntermediate {
            val fluid = json.get("fluid")?.let { convertToExpression(it) }
                    ?: throw error("Didn't specify fluid in fluid stack")
            val count = json.get("count")?.let { convertToExpression(it) } ?: NumberValue(1)
            return RecipeFluidStackIntermediate(fluid, count)
        }

        override fun write(obj: RecipeFluidStack) = json {
            "fluid" to obj.stack.fluid
            "count" to obj.count
        }

        override fun read(packet: PacketBuffer) = RecipeFluidStack(packet.readFluidStack())

        override fun write(packet: PacketBuffer, obj: RecipeFluidStack) {
            packet.writeFluidStack(obj.stack)
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