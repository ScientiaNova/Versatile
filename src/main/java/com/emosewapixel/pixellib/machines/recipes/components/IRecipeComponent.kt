package com.emosewapixel.pixellib.machines.recipes.components

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import com.emosewapixel.pixellib.machines.properties.implementations.processing.IProcessingHandler
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.RecipeList
import com.emosewapixel.pixellib.machines.recipes.components.grouping.RecipeComponentFamily

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