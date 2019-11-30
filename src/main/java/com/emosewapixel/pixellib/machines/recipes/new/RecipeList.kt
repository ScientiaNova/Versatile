package com.emosewapixel.pixellib.machines.recipes.new

import com.emosewapixel.pixellib.machines.gui.layout.GUIComponentGroup
import net.minecraft.block.Block
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent

class RecipeList(val name: ResourceLocation, vararg components: IRecipeComponent<*>, val genJEIPage: Boolean = true) {
    private val recipes = mutableSetOf<Recipe>()
    val blocksImplementing = mutableListOf<Block>()
    val inputMap = mutableMapOf<String, MutableSet<Recipe>>()
    val localizedName = TranslationTextComponent("recipe_list.$name")
    val recipeComponents = components.groupBy(IRecipeComponent<*>::name).mapValues { it.value.first() }

    fun addRecipe(recipe: Recipe) {
        if (recipeComponents.values.all { it.isRecipeValid(recipe) }) {
            recipes += recipe
            recipeComponents.values.forEach { it.onRecipeAdded(recipe, this) }
        }
    }

    fun findRecipe(machineInterface: MachineRecipeInterface) =
            recipeComponents.values.fold(recipes.toList()) { list, component -> component.findRecipe(list, machineInterface) }.firstOrNull()

    fun removeRecipe(recipe: Recipe) {
        if (recipes.remove(recipe)) recipeComponents.values.forEach { it.onRecipeRemoved(recipe, this) }
    }

    fun createComponentGroup(machineInterface: MachineRecipeInterface): GUIComponentGroup {
        return GUIComponentGroup()
    }

    fun addExtraInfo(recipe: Recipe) = recipeComponents.values.fold(emptyList<String>()) { list, component -> component.addExtraInfo(recipe, list) }
}