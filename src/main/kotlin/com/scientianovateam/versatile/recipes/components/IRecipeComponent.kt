package com.scientianovateam.versatile.recipes.components

import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.layout.IGUIComponent
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.properties.IValueProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.IProcessingHandler
import com.scientianovateam.versatile.recipes.Recipe
import com.scientianovateam.versatile.recipes.lists.RecipeList
import com.scientianovateam.versatile.recipes.components.grouping.RecipeComponentFamily

interface IRecipeComponent<T> {
    val name: String

    val family: RecipeComponentFamily

    fun isRecipeValid(recipe: Recipe): Boolean

    fun onRecipeAdded(recipe: Recipe) {}

    fun findRecipe(recipeList: RecipeList, recipes: List<Recipe>, machine: BaseTileEntity) = recipes

    fun onRecipeRemoved(recipe: Recipe) {}

    fun addExtraInfo() = emptyList<(Recipe) -> String>()

    fun addGUIComponents(machine: BaseTileEntity?) = emptyList<IGUIComponent>()

    fun addRecipeGUIComponents(machine: BaseTileEntity?, recipe: Recipe) = emptyList<IGUIComponent>()

    fun addDefaultProperty(te: BaseTileEntity, properties: MutableList<ITEBoundProperty>)

    fun getProgressBarDouble(machine: BaseTileEntity?): IValueProperty<Double>? = null

    fun getRecipeProgressBarDouble(machine: BaseTileEntity?, recipe: Recipe): IValueProperty<Double>? = null

    fun getProcessingHandler(machine: BaseTileEntity): IProcessingHandler? = null
}