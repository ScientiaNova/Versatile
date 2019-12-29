package com.scientianovateam.versatile.machines.recipes.syncing

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.machines.recipes.RecipeSerializer
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.network.PacketBuffer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistryEntry

object SingleRecipeSerializer : ForgeRegistryEntry<IRecipeSerializer<*>>(), IRecipeSerializer<SingleRecipe> {
    init {
        registryName = "single".toResLocV()
    }

    override fun read(recipeId: ResourceLocation, json: JsonObject): SingleRecipe {
        json.addProperty("name", recipeId.toString())
        return SingleRecipe(RecipeSerializer.read(json))
    }

    override fun read(recipeId: ResourceLocation, buffer: PacketBuffer) = SingleRecipe(RecipeSerializer.read(buffer))

    override fun write(buffer: PacketBuffer, recipe: SingleRecipe) {
        RecipeSerializer.write(buffer, recipe.recipe)
    }
}