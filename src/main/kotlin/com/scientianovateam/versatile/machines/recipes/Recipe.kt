package com.scientianovateam.versatile.machines.recipes

import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponentHandler
import com.google.gson.JsonObject

class Recipe(val recipeList: RecipeList, val name: String, vararg componentHandlers: IRecipeComponentHandler<*>): IJSONSerializer<Recipe, JsonObject> {
    private val map = componentHandlers.map { it.pairedComponentType to it }.toMap()

    init {
        recipeList.addRecipe(this)
    }

    val page by lazy { recipeList.createRecipeBasedComponentGroup(null, this) }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(component: IRecipeComponent<T>) = map[component.javaClass] as? IRecipeComponentHandler<T>

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(clazz: Class<out IRecipeComponent<T>>) = map[clazz] as? IRecipeComponentHandler<T>

    override fun read(json: JsonObject): Recipe {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun write(obj: Recipe): JsonObject {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}