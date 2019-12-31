package com.scientianovateam.versatile.recipes

import com.scientianovateam.versatile.common.extensions.plus
import com.scientianovateam.versatile.common.serialization.IJSONIntermediate
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.recipes.lists.IRecipeLIst
import com.scientianovateam.versatile.velisp.evaluated.MaterialValue
import net.minecraft.util.ResourceLocation

data class RecipeMaterialTemplate(val recipeList: IRecipeLIst, val baseName: ResourceLocation, val componentIntermediates: List<IJSONIntermediate<out IRecipeComponentHandler<*>>>) {
    fun create(material: Material) = Recipe(recipeList, baseName + "/${material.name}", componentIntermediates.map { it.resolve(mapOf("mat" to MaterialValue(material))) })
}