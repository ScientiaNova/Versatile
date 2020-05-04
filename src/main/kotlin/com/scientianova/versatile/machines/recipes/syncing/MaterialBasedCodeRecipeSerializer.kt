package com.scientianova.versatile.machines.recipes.syncing

import com.scientianova.versatile.common.extensions.toResLoc
import com.google.gson.JsonObject
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.network.PacketBuffer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistryEntry

object MaterialBasedCodeRecipeSerializer : ForgeRegistryEntry<IRecipeSerializer<*>>(), IRecipeSerializer<MaterialBasedCodeRecipe> {
    init {
        registryName = "versatile:material_based".toResLoc()
    }

    override fun read(recipeId: ResourceLocation, json: JsonObject) = MaterialBasedCodeRecipe(recipeId,
            json.get("material").asString ?: "",
            json.getAsJsonObject("recipe") ?: JsonObject()
    )

    override fun read(recipeId: ResourceLocation, buffer: PacketBuffer): MaterialBasedCodeRecipe? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun write(buffer: PacketBuffer, recipe: MaterialBasedCodeRecipe) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}