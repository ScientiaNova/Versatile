package com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.machines.recipes.components.ingredients.utility.TagStack
import com.scientianovateam.versatile.machines.recipes.components.ingredients.utility.times
import com.scientianovateam.versatile.machines.recipes.components.ingredients.utility.toStack
import net.minecraft.fluid.Fluid
import net.minecraft.tags.FluidTags
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

    override val serializer = Serializer

    object Serializer : IRegisterableJSONSerializer<RecipeFluidTagStack, JsonObject> {
        override val registryName = "versatile:tag_stack".toResLoc()

        override fun read(json: JsonObject): RecipeFluidTagStack {
            val tag = json.getAsJsonPrimitive("tag")?.asString?.toResLoc()?.let { FluidTags.Wrapper(it) }
                    ?: FluidTags.Wrapper("empty".toResLoc())
            val count = if (json.has("count")) json.getAsJsonPrimitive("count").asNumber?.toInt() ?: 1 else 1
            return RecipeFluidTagStack(tag * count)
        }

        override fun write(obj: RecipeFluidTagStack) = json {
            "tag" to obj.tag
            if (obj.count > 1) "count" to obj.count
        }
    }
}

operator fun MutableCollection<IRecipeStack<FluidStack>>.plusAssign(stack: TagStack<Fluid>) = plusAssign(RecipeFluidTagStack(stack))
fun Tag<Fluid>.toRStack(count: Int = 1000) = RecipeFluidTagStack(this.toStack(count))
fun TagStack<Fluid>.r() = RecipeFluidTagStack(this)