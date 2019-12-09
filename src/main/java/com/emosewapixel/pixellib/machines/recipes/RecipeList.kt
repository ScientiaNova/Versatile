package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.PixelLib
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.layout.DefaultSizeConstants
import com.emosewapixel.pixellib.machines.gui.layout.GUIComponentGroup
import com.emosewapixel.pixellib.machines.gui.layout.GUIPage
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.gui.textures.ProgressBar
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import com.emosewapixel.pixellib.machines.properties.implementations.primitives.IncrementingDoubleProperty
import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponent
import com.emosewapixel.pixellib.machines.recipes.components.grouping.IOType
import com.google.common.collect.HashMultimap
import net.minecraft.block.Block
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.sqrt

open class RecipeList(val name: ResourceLocation, vararg components: IRecipeComponent<*>, val progressBar: ProgressBar = BaseTextures.ARROW_BAR, val genJEIPage: Boolean = true) {
    private val recipes = mutableMapOf<String, Recipe>()
    val blocksImplementing = mutableListOf<Block>()
    val inputMap = HashMultimap.create<String, Recipe>()
    val localizedName = TranslationTextComponent("recipe_list.$name")
    val recipeComponents = components.groupBy(IRecipeComponent<*>::name).mapValues { it.value.first() }

    init {
        RecipeLists += this
    }

    fun getRecipes() = recipes.toMap()

    fun addRecipe(recipe: Recipe) {
        if (recipe.recipeList != this) return
        if (recipeComponents.values.all { it.isRecipeValid(recipe) }) {
            recipes[recipe.name] = recipe
            recipeComponents.values.forEach { it.onRecipeAdded(recipe) }
        } else PixelLib.LOGGER.error("Invalid recipe ${recipe.name} for recipe list $name")
    }

    fun findRecipe(machine: BaseTileEntity) =
            recipeComponents.values.fold(recipes.values.toList()) { list, component -> component.findRecipe(this, list, machine) }.firstOrNull()

    fun removeRecipe(recipe: Recipe) {
        if (recipes.remove(recipe.name) != null) recipeComponents.values.forEach { it.onRecipeRemoved(recipe) }
    }

    fun removeRecipe(name: String) {
        recipes.remove(name)?.let { recipe -> recipeComponents.values.forEach { it.onRecipeRemoved(recipe) } }
    }

    open fun createComponentGroup(machine: BaseTileEntity? = null, progressProperty: IValueProperty<Double> = IncrementingDoubleProperty()): GUIComponentGroup {
        val components = recipeComponents.values.groupBy(IRecipeComponent<*>::family).entries.groupBy { it.key.io }.mapValues {
            val groups = it.value.map { entry -> entry.value.flatMap { component -> component.addGUIComponents(machine) } }
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

        val extraHeight = if (machine == null) recipeComponents.values.flatMap { it.addExtraInfo() }.size * 10 + 2 + DefaultSizeConstants.TEXT_HEIGHT else 0

        return GUIComponentGroup(extraHeight = extraHeight).apply {
            add(inputSide, 0, (maxHeight - inputSide.height) / 2)

            progressBar(progressProperty) {
                texture = progressBar
                x = inputSide.width + 10
                y = (maxHeight - 16) / 2
            }

            add(outputSide, inputSide.width + 42, (maxHeight - outputSide.height) / 2)
        }
    }

    open fun createRecipeBasedComponentGroup(machine: BaseTileEntity?, recipe: Recipe, progressProperty: IValueProperty<Double> = IncrementingDoubleProperty()): GUIComponentGroup {
        val components = recipeComponents.values.groupBy(IRecipeComponent<*>::family).entries.groupBy { it.key.io }.mapValues {
            val groups = it.value.map { entry -> entry.value.flatMap { component -> component.addRecipeGUIComponents(machine, recipe) } }
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
                texture = progressBar
                x = inputSide.width + 10
                y = (maxHeight - 16) / 2
            }

            add(outputSide, inputSide.width + 42, (maxHeight - outputSide.height) / 2)

            if (machine == null) {
                val info = recipeComponents.values.flatMap { component -> component.addExtraInfo().map { it.invoke(recipe) } }
                for ((index, text) in info.withIndex()) label(text, 0, maxHeight + index * 10 + 2)
            }
        }
    }
}