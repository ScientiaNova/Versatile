package com.scientianovateam.versatile.common.registry

import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks.IRecipeStack
import com.scientianovateam.versatile.materialsystem.properties.MatProperty
import com.scientianovateam.versatile.materialsystem.properties.ObjTypeProperty
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

object BaseRegistries {
    @JvmField
    val RECIPE_ITEM_STACK_SERIALIZERS = Registry<IRegisterableJSONSerializer<out IRecipeStack<ItemStack>, JsonObject>>()

    @JvmField
    val RECIPE_FLUID_STACK_SERIALIZERS = Registry<IRegisterableJSONSerializer<out IRecipeStack<FluidStack>, JsonObject>>()

    @JvmField
    val RECIPE_COMPONENT_HANDLER_SERIALIZERS = Registry<IRegisterableJSONSerializer<out IRecipeComponentHandler<*>, out JsonElement>>()

    @JvmField
    val MATERIAL_PROPERTIES = Registry<MatProperty<*>>()

    @JvmField
    val OBJECT_TYPE_PROPERTIES = Registry<ObjTypeProperty<*>>()
}