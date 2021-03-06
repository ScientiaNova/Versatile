package com.scientianova.versatile.machines.properties.implementations.recipes

import com.scientianova.versatile.machines.properties.IVariableProperty
import com.scientianova.versatile.machines.recipes.Recipe
import com.scientianova.versatile.machines.recipes.RecipeList

open class RecipeProperty(val recipeList: RecipeList, value: Recipe? = null) : IVariableProperty<Recipe?> {
    override var value: Recipe? = value
        protected set

    override fun setValue(new: Recipe?, causeUpdate: Boolean) {
        value = new
    }

    override fun clone() = RecipeProperty(recipeList, value)
}