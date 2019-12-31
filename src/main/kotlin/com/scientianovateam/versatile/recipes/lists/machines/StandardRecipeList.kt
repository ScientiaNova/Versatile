package com.scientianovateam.versatile.recipes.lists.machines

import com.google.common.collect.HashMultimap
import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.layout.DefaultSizeConstants
import com.scientianovateam.versatile.machines.gui.layout.GUIComponentGroup
import com.scientianovateam.versatile.machines.gui.layout.GUIPage
import com.scientianovateam.versatile.machines.gui.layout.IGUIComponent
import com.scientianovateam.versatile.machines.gui.textures.BaseTextures
import com.scientianovateam.versatile.machines.gui.textures.updating.ProgressBar
import com.scientianovateam.versatile.recipes.Recipe
import com.scientianovateam.versatile.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.recipes.components.grouping.IOType
import com.scientianovateam.versatile.recipes.lists.IRecipeLIst
import com.scientianovateam.versatile.recipes.lists.RecipeLists
import net.minecraft.block.Block
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.sqrt

open class StandardRecipeList(final override val name: ResourceLocation, vararg components: IRecipeComponent<*>, val progressBar: ProgressBar = BaseTextures.ARROW_BAR, override val genJEIPage: Boolean = true): IRecipeLIst {
    override val recipes = mutableMapOf<ResourceLocation, Recipe>()
    override val blocksImplementing = mutableListOf<Block>()
    override val inputMap = HashMultimap.create<String, Recipe>()
    override val localizedName = TranslationTextComponent("recipe_list.$name")
    override val recipeComponents = components.map { it.name to it }.toMap()

    init {
        RecipeLists += this
    }

    override fun addRecipe(recipe: Recipe, vanillaRecipeMap: MutableMap<IRecipeType<*>, MutableMap<ResourceLocation, IRecipe<*>>>?) {
        if (recipe.recipeList != this) return
        if (recipeComponents.values.all { it.isRecipeValid(recipe) }) {
            recipes[recipe.name] = recipe
            recipeComponents.values.forEach { it.onRecipeAdded(recipe) }
        } else Versatile.LOGGER.error("Invalid recipe ${recipe.name} for recipe list $name")
    }

    override fun findRecipe(machine: BaseTileEntity) = recipeComponents.values.fold(recipes.values.toList()) { list, component ->
        component.findRecipe(this, list, machine)
    }.firstOrNull()

    override fun clear() {
        recipes.clear()
        inputMap.clear()
    }

    override fun createComponentGroup(machine: BaseTileEntity?): GUIComponentGroup {
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

    override fun createRecipeBasedComponentGroup(machine: BaseTileEntity?, recipe: Recipe): GUIComponentGroup {
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