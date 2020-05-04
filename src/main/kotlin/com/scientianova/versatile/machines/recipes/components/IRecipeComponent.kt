package com.scientianova.versatile.machines.recipes.components

import com.scientianova.versatile.machines.BaseTileEntity
import com.scientianova.versatile.machines.gui.layout.IGUIComponent
import com.scientianova.versatile.machines.properties.ITEBoundProperty
import com.scientianova.versatile.machines.properties.IValueProperty
import com.scientianova.versatile.machines.properties.implementations.processing.IProcessingHandler
import com.scientianova.versatile.machines.recipes.Recipe
import com.scientianova.versatile.machines.recipes.RecipeList
import com.scientianova.versatile.machines.recipes.components.grouping.RecipeComponentFamily

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