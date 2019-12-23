package com.scientianovateam.versatile.common.serialization

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.registry.Registry
import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks.IRecipeStack
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

object SerializerRegistries {
    @JvmField
    val RECIPE_ITEM_STACK_SERIALIZERS = Registry<IRegisterableJSONSerializer<out IRecipeStack<ItemStack>, JsonObject>>()

    @JvmField
    val RECIPE_FLUID_STACK_SERIALIZERS = Registry<IRegisterableJSONSerializer<out IRecipeStack<FluidStack>, JsonObject>>()

    @JvmField
    val RECIPE_COMPONENT_HANDLER_SERIALIZERS = Registry<IRegisterableJSONSerializer<out IRecipeComponentHandler<*>, out JsonElement>>()
}