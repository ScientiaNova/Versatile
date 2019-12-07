package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.machines.gui.layout.GUIComponentGroup
import com.emosewapixel.pixellib.machines.gui.layout.GUIPage
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.gui.textures.ProgressBar
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import com.emosewapixel.pixellib.machines.properties.implementations.IncrementingDoubleProperty
import net.minecraft.block.Block
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.sqrt

open class RecipeList(val name: ResourceLocation, vararg components: IRecipeComponent<*, *>, val progressBar: ProgressBar = BaseTextures.ARROW_BAR, val genJEIPage: Boolean = true) {
    val recipes = mutableSetOf<Recipe>()
    val blocksImplementing = mutableListOf<Block>()
    val inputMap = mutableMapOf<String, MutableSet<Recipe>>()
    val localizedName = TranslationTextComponent("recipe_list.$name")
    val recipeComponents = components.groupBy(IRecipeComponent<*, *>::name).mapValues { it.value.first() }

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

    open fun createComponentGroup(machineInterface: MachineRecipeInterface? = null, progressProperty: IValueProperty<Double> = IncrementingDoubleProperty()): GUIComponentGroup {
        val components = recipeComponents.values.groupBy(IRecipeComponent<*, *>::family).entries.groupBy { it.key.io }.mapValues {
            val groups = it.value.map { entry -> entry.value.flatMap { component -> component.addGUIComponents(machineInterface) } }
                    .filter(List<IGUIComponent>::isNotEmpty).map { list ->
                        val width = list.first().width
                        val height = list.first().height
                        val columns = ceil(sqrt(list.size.toDouble())).toInt()
                        GUIComponentGroup().apply {
                            list.forEachIndexed { index, component ->
                                add(component, index % columns * width, index / columns * height)
                            }
                        }
                    }
            val maxHeight = groups.map(GUIPage::height).max() ?: 0
            var xStart = 0
            GUIComponentGroup().apply {
                groups.forEach { group ->
                    add(group, xStart, (maxHeight - group.height) / 2)
                    xStart += group.width + 6
                }
            }
        }

        val inputSide = components[IOType.INPUT] ?: GUIComponentGroup()
        val outputSide = components[IOType.OUTPUT] ?: GUIComponentGroup()

        val maxHeight = max(inputSide.height, outputSide.height)

        return GUIComponentGroup().apply {
            add(inputSide, 0, (maxHeight - inputSide.height) / 2)

            progressBar(progressProperty) {
                bar = progressBar
                x = inputSide.width + 10
                y = (maxHeight - 16) / 2
            }

            add(outputSide, inputSide.width + 42, (maxHeight - outputSide.height) / 2)

            recipeComponents.values.forEach { it.addToPage(this, machineInterface) }
        }
    }

    open fun createRecipeBasedComponentGroup(machineInterface: MachineRecipeInterface?, recipe: Recipe, progressProperty: IValueProperty<Double>): GUIComponentGroup {
        val components = recipeComponents.values.groupBy(IRecipeComponent<*, *>::family).entries.groupBy { it.key.io }.mapValues {
            val groups = it.value.map { entry -> entry.value.flatMap { component -> component.addRecipeGUIComponents(machineInterface, recipe) } }
                    .filter(List<IGUIComponent>::isNotEmpty).map { list ->
                        val width = list.first().width
                        val height = list.first().height
                        val columns = ceil(sqrt(list.size.toDouble())).toInt()
                        GUIComponentGroup().apply {
                            list.forEachIndexed { index, component ->
                                add(component, index % columns * width, index / columns * height)
                            }
                        }
                    }
            val maxHeight = groups.map(GUIPage::height).max() ?: 0
            var xStart = 0
            GUIComponentGroup().apply {
                groups.forEach { group ->
                    add(group, xStart, (maxHeight - group.height) / 2)
                    xStart += group.width + 6
                }
            }
        }

        val inputSide = components[IOType.INPUT] ?: GUIComponentGroup()
        val outputSide = components[IOType.OUTPUT] ?: GUIComponentGroup()

        val maxHeight = max(inputSide.height, outputSide.height)

        return GUIComponentGroup().apply {
            add(inputSide, 0, (maxHeight - inputSide.height) / 2)

            progressBar(progressProperty) {
                bar = progressBar
                x = inputSide.width + 10
                y = (maxHeight - 16) / 2
            }

            add(outputSide, inputSide.width + 42, (maxHeight - outputSide.height) / 2)

            recipeComponents.values.forEach { it.addToPage(this, machineInterface) }
        }
    }

    open fun addExtraInfo(recipe: Recipe) = recipeComponents.values.fold(emptyList<String>()) { list, component -> component.addExtraInfo(recipe, list) }
}