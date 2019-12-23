package com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.registry.BaseRegistries
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import com.scientianovateam.versatile.common.serialization.SerializerRegistries
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

open class ChancedRecipeStack<T>(val value: IRecipeStack<T>, val chance: Float = 1f) : IRecipeStack<T> by value

fun <T> IRecipeStack<T>.chanced(chance: Float = 1f) = ChancedRecipeStack(this, chance)

object ChancedRecipeItemStackSerializer : IJSONSerializer<ChancedRecipeStack<ItemStack>, JsonObject> {
    override fun read(json: JsonObject): ChancedRecipeStack<ItemStack> {
        val stack = json.entrySet().firstOrNull { it.key != "chance" }?.let {
            SerializerRegistries.RECIPE_ITEM_STACK_SERIALIZERS[it.key.toResLoc("versatile")]?.read(it.value.asJsonObject)
        } ?: RecipeItemStack.EMPTY
        val chance = if (json.has("chance")) json.getAsJsonPrimitive("chance").asFloat else 1f
        return ChancedRecipeStack(stack, chance)
    }

    override fun write(obj: ChancedRecipeStack<ItemStack>) = json {
        obj.serializer.registryName to obj.value.serialize()
        if (obj.chance < 1f) "chance" to obj.chance
    }
}

object ChancedRecipeFluidStackSerializer : IJSONSerializer<ChancedRecipeStack<FluidStack>, JsonObject> {
    override fun read(json: JsonObject): ChancedRecipeStack<FluidStack> {
        val stack = json.entrySet().firstOrNull { it.key != "chance" }?.let {
            SerializerRegistries.RECIPE_FLUID_STACK_SERIALIZERS[it.key.toResLoc("versatile")]?.read(it.value.asJsonObject)
        } ?: RecipeFluidStack.EMPTY
        val chance = if (json.has("chance")) json.getAsJsonPrimitive("chance").asFloat else 1f
        return ChancedRecipeStack(stack, chance)
    }

    override fun write(obj: ChancedRecipeStack<FluidStack>) = json {
        obj.serializer.registryName to obj.value.serialize()
        if (obj.chance < 1f) "chance" to obj.chance
    }
}