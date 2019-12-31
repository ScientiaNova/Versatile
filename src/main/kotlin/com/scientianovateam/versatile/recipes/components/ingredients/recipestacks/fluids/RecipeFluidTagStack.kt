package com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.fluids

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.serialization.IRegisterableSerializer
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.IRecipeStack
import com.scientianovateam.versatile.recipes.components.ingredients.utility.TagStack
import com.scientianovateam.versatile.recipes.components.ingredients.utility.times
import com.scientianovateam.versatile.recipes.components.ingredients.utility.toStack
import com.scientianovateam.versatile.velisp.convertToExpression
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.network.PacketBuffer
import net.minecraft.tags.FluidTags
import net.minecraft.tags.Tag
import net.minecraftforge.fluids.FluidStack

class RecipeFluidTagStack(stack: TagStack<Fluid>) : IRecipeStack<FluidStack> {
    val tag = stack.tag

    override val count = stack.count

    override val stacks get() = tag.allElements.map { it * count }

    override val singleStack = (tag.allElements.minBy { it.registryName.toString() } ?: Fluids.EMPTY) * count

    override val names get() = tag.allElements.map { it.registryName!! }.toSet()

    override fun matches(other: FluidStack) = count <= other.amount && other.fluid in tag

    override fun matchesWithoutCount(other: FluidStack) = other.fluid in tag

    override val serializer = Serializer

    object Serializer : IRegisterableSerializer<RecipeFluidTagStack, JsonObject> {
        override val registryName = "versatile:tag_stack".toResLoc()

        override fun read(json: JsonObject): RecipeFluidTagStackIntermediate {
            val tag = json.get("item")?.let { convertToExpression(it) }
                    ?: throw error("Didn't specify tag in fluid tag stack")
            val count = json.get("count")?.let { convertToExpression(it) } ?: NumberValue(1)
            return RecipeFluidTagStackIntermediate(tag, count)
        }

        override fun write(obj: RecipeFluidTagStack) = json {
            "tag" to obj.tag
            "count" to obj.count
        }

        override fun read(packet: PacketBuffer) = RecipeFluidTagStack(FluidTags.Wrapper(packet.readResourceLocation()) * packet.readVarInt())

        override fun write(packet: PacketBuffer, obj: RecipeFluidTagStack) {
            packet.writeResourceLocation(obj.tag.id)
            packet.writeVarInt(obj.count)
        }
    }
}

operator fun MutableCollection<IRecipeStack<FluidStack>>.plusAssign(stack: TagStack<Fluid>) = plusAssign(RecipeFluidTagStack(stack))
fun Tag<Fluid>.toRStack(count: Int = 1000) = RecipeFluidTagStack(this.toStack(count))
fun TagStack<Fluid>.r() = RecipeFluidTagStack(this)