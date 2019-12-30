package com.scientianovateam.versatile.machines.properties.implementations.recipes

import com.scientianovateam.versatile.machines.properties.IVariableProperty
import com.scientianovateam.versatile.recipes.Recipe
import com.scientianovateam.versatile.recipes.lists.RecipeList

open class RecipeProperty(val recipeList: RecipeList, value: Recipe? = null) : IVariableProperty<Recipe?> {
    override var value: Recipe? = value
        protected set

    override fun setValue(new: Recipe?, causeUpdate: Boolean) {
        value = new
    }

    override fun clone() = RecipeProperty(recipeList, value)
}