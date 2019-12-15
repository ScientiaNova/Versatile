package com.emosewapixel.pixellib.machines.properties.implementations.recipes

import com.emosewapixel.pixellib.machines.properties.IVariableProperty
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.RecipeList

open class RecipeProperty(val recipeList: RecipeList, value: Recipe? = null) : IVariableProperty<Recipe?> {
    override var value: Recipe? = value
        protected set

    override fun setValue(new: Recipe?, causeUpdate: Boolean) {
        value = new
    }

    override fun clone() = RecipeProperty(recipeList, value)
}