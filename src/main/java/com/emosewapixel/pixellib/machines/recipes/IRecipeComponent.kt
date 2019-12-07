package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.machines.gui.layout.GUIPage
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.IValueProperty

interface IRecipeComponent<T, P : IValueProperty<*>> {
    val name: String

    val family: RecipeComponentFamily

    fun isRecipeValid(recipe: Recipe): Boolean

    fun onRecipeAdded(recipe: Recipe, list: RecipeList)

    fun findRecipe(recipes: List<Recipe>, machineInterface: MachineRecipeInterface): List<Recipe>

    fun onRecipeRemoved(recipe: Recipe, list: RecipeList)

    fun addExtraInfo(recipe: Recipe, list: List<String>): List<String>

    fun addGUIComponents(machineInterface: MachineRecipeInterface?): List<IGUIComponent>

    fun addRecipeGUIComponents(machineInterface: MachineRecipeInterface?, recipe: Recipe): List<IGUIComponent>

    fun addToPage(page: GUIPage, te: MachineRecipeInterface?)

    fun addDefaultProperty(machineInterface: MachineRecipeInterface): List<ITEBoundProperty>
}