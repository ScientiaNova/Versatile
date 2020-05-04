package com.scientianova.versatile.machines.recipes

import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.BaseContainer
import com.scientianovateam.versatile.machines.gui.layout.DefaultSizeConstants
import com.scientianovateam.versatile.machines.gui.layout.GUIComponentGroup
import com.scientianovateam.versatile.machines.gui.layout.GUIPage
import com.scientianovateam.versatile.machines.gui.layout.IGUIComponent
import com.scientianovateam.versatile.machines.gui.textures.BaseTextures
import com.scientianovateam.versatile.machines.gui.textures.updating.ProgressBar
import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.machines.recipes.components.grouping.IOType
import com.google.common.collect.HashMultimap
import com.scientianova.versatile.Versatile
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
    open val recipeTransferFunction: ((Recipe, BaseContainer) -> Unit)? = null

    init {
        RecipeLists += this
    }

    fun getRecipes() = recipes.toMap()

    fun addRecipe(recipe: Recipe) {
        if (recipe.recipeList != this) return
        if (recipeComponents.values.all { it.isRecipeValid(recipe) }) {
            recipes[recipe.name] = recipe
            recipeComponents.values.forEach { it.onRecipeAdded(recipe) }
        } else _root_ide_package_.com.scientianova.versatile.Versatile.LOGGER.error("Invalid recipe ${recipe.name} for recipe list $name")
    }

    fun findRecipe(machine: BaseTileEntity) = recipeComponents.values.fold(recipes.values.toList()) { list, component ->
        component.findRecipe(this, list, machine)
    }.firstOrNull()

    fun removeRecipe(recipe: Recipe) {
        if (recipes.remove(recipe.name) != null) recipeComponents.values.forEach { it.onRecipeRemoved(recipe) }
    }

    fun removeRecipe(name: String) {
        recipes.remove(name)?.let { recipe -> recipeComponents.values.forEach { it.onRecipeRemoved(recipe) } }
    }

    open fun createComponentGroup(machine: BaseTileEntity? = null): GUIComponentGroup {
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

        val extraHeight = if (machine == null) recipeComponents.values.flatMap { it.addExtraInfo() }.lastIndex * 10 + 2 + DefaultSizeConstants.TEXT_HEIGHT else 0

        return GUIComponentGroup(extraHeight = extraHeight).apply {
            add(inputSide, 0, (maxHeight - inputSide.height) / 2)

            recipeComponents.values.map { it.getProgressBarDouble(machine) }.firstOrNull { it != null }?.let {
                progressBar(it) {
                    texture = progressBar
                    x = inputSide.width + 10
                    y = (maxHeight - 16) / 2
                }
            } ?: image {
                texture = progressBar.background
                width = 22
                height = 16
                x = inputSide.width + 10
                y = (maxHeight - 16) / 2
            }

            add(outputSide, inputSide.width + 42, (maxHeight - outputSide.height) / 2)
        }
    }

    open fun createRecipeBasedComponentGroup(machine: BaseTileEntity?, recipe: Recipe): GUIComponentGroup {
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

        return GUIComponentGroup().apply {
            add(inputSide, 0, (maxHeight - inputSide.height) / 2)

            recipeComponents.values.let { list ->
                if (machine != null) list.map { it.getProgressBarDouble(machine) }
                else list.map { it.getRecipeProgressBarDouble(machine, recipe) }
            }.firstOrNull { it != null }?.let {
                progressBar(it) {
                    texture = progressBar
                    x = inputSide.width + 10
                    y = (maxHeight - 16) / 2
                }
            } ?: image {
                texture = progressBar.background
                width = 22
                height = 16
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