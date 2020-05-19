package com.scientianova.versatile.resources.recipes

import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeType
import net.minecraft.util.ResourceLocation

class RecipeHandler(private val map: MutableMap<IRecipeType<*>, MutableMap<ResourceLocation, IRecipe<*>>>) {
    fun <T : IRecipe<*>> add(type: IRecipeType<in T>, name: ResourceLocation, recipe: T) {
        map.getOrPut(type, { mutableMapOf() })[name] = recipe
    }

    fun remove(type: IRecipeType<*>, name: ResourceLocation) = map[type]?.remove(name)
}