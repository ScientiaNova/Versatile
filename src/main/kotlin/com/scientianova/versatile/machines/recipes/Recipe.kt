package com.scientianova.versatile.machines.recipes

import com.scientianova.versatile.machines.recipes.components.IRecipeComponent
import com.scientianova.versatile.machines.recipes.components.IRecipeComponentHandler

class Recipe(val recipeList: RecipeList, val name: String, vararg componentHandlers: IRecipeComponentHandler<*>) {
    private val map = componentHandlers.map { it.pairedComponentType to it }.toMap()

    init {
        recipeList.addRecipe(this)
    }

    val page by lazy { recipeList.createRecipeBasedComponentGroup(null, this) }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(component: IRecipeComponent<T>) = map[component.javaClass] as? IRecipeComponentHandler<T>

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(clazz: Class<out IRecipeComponent<T>>) = map[clazz] as? IRecipeComponentHandler<T>
}