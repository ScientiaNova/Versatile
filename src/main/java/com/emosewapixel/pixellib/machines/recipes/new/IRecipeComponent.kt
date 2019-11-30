package com.emosewapixel.pixellib.machines.recipes.new

interface IRecipeComponent<T> {
    val name: String

    val family: RecipeComponentFamily

    fun isRecipeValid(recipe: Recipe): Boolean

    fun onRecipeAdded(recipe: Recipe, list: RecipeList)

    fun findRecipe(recipes: List<Recipe>, machineInterface: MachineRecipeInterface): List<Recipe>

    fun onRecipeRemoved(recipe: Recipe, list: RecipeList)

    fun addExtraInfo(recipe: Recipe, list: List<String>): List<String>
}