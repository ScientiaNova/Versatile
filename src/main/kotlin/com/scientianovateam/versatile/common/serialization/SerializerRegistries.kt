package com.scientianovateam.versatile.common.serialization

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.registry.Registry
import com.scientianovateam.versatile.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.IRecipeStack
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

val RECIPE_ITEM_STACK_SERIALIZERS = Registry<IRegisterableSerializer<out IRecipeStack<ItemStack>, JsonObject>>()

val RECIPE_FLUID_STACK_SERIALIZERS = Registry<IRegisterableSerializer<out IRecipeStack<FluidStack>, JsonObject>>()

val RECIPE_COMPONENT_HANDLER_SERIALIZERS = Registry<IRegisterableSerializer<out IRecipeComponentHandler<*>, JsonElement>>()

fun <T : IRegisterableSerializer<*, *>> Registry<T>.register(serializer: T) = set(serializer.registryName, serializer)

fun <T : IRegisterableSerializer<*, *>> Registry<T>.registerAll(vararg serializers: T) = serializers.forEach(::register)

fun <T : IRegisterableJSONSerializer<*, *>> Registry<T>.register(serializer: T) = set(serializer.registryName, serializer)

fun <T : IRegisterableJSONSerializer<*, *>> Registry<T>.registerAll(vararg serializers: T) = serializers.forEach(::register)