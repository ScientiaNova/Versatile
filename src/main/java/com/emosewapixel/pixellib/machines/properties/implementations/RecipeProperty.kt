package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.machines.properties.IVariableProperty
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.RecipeList

open class RecipeProperty(val recipeList: RecipeList) : IVariableProperty<Recipe?> {
    override var value: Recipe? = null

    override fun setValue(new: Recipe?, causeUpdate: Boolean) {
        value = new
    }

    override fun createDefault() = RecipeProperty(recipeList)
}