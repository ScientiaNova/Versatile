package com.scientianovateam.versatile.recipes

import com.scientianovateam.versatile.common.serialization.IJSONIntermediate
import com.scientianovateam.versatile.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.recipes.lists.RecipeList
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.velisp.evaluated.MaterialValue

data class RecipeMaterialTemplate(val recipeList: RecipeList, val baseName: String, val componentIntermediates: List<IJSONIntermediate<out IRecipeComponentHandler<*>>>) {
    fun create(material: Material) = Recipe(recipeList, "$baseName/${material.name}", componentIntermediates.map { it.resolve(mapOf("mat" to MaterialValue(material))) })
}