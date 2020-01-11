package com.scientianovateam.versatile.data.recipe

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.data.generation.GSON
import com.scientianovateam.versatile.recipes.lists.IRecipeLIst
import com.scientianovateam.versatile.recipes.lists.serialize
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import java.io.IOException

class RecipeListProvider(private val dataGenerator: DataGenerator) : IDataProvider {
    private val lists = mutableListOf<IRecipeLIst>()

    operator fun IRecipeLIst.unaryPlus() = this.apply {
        lists += this
    }

    override fun act(cache: DirectoryCache) {
        lists.forEach {
            try {
                IDataProvider.save(GSON, cache, it.serialize(), dataGenerator.outputFolder.resolve("data/${it.name.namespace}/registries/recipe_lists/${it.name.path}.json"))
            } catch (e: IOException) {
                Versatile.LOGGER.error("Couldn't save recipe list")
            }
        }
    }

    override fun getName() = "Recipe Lists"
}