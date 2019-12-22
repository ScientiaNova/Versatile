package com.scientianovateam.versatile.machines.recipes

import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.BaseContainer
import com.scientianovateam.versatile.machines.gui.layout.GUIComponentGroup
import com.scientianovateam.versatile.machines.gui.layout.GUIPage
import com.scientianovateam.versatile.machines.gui.layout.IGUIComponent
import com.scientianovateam.versatile.machines.gui.textures.BaseTextures
import com.scientianovateam.versatile.machines.gui.textures.updating.ProgressBar
import com.scientianovateam.versatile.machines.properties.implementations.recipes.RecipeProperty
import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.machines.recipes.components.grouping.IOType
import net.minecraft.util.ResourceLocation
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.sqrt

open class AutomationRecipeList(name: ResourceLocation, vararg components: IRecipeComponent<*>, progressBar: ProgressBar = BaseTextures.ARROW_BAR, genJEIPage: Boolean = true) : RecipeList(name, *components, progressBar = progressBar, genJEIPage = genJEIPage) {
    override val recipeTransferFunction: (Recipe, BaseContainer) -> Unit = { recipe, container ->
        (container.te.teProperties["recipe"] as? RecipeProperty)?.setValue(recipe)
    }

    override fun createRecipeBasedComponentGroup(machine: BaseTileEntity?, recipe: Recipe): GUIComponentGroup {
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

            recipeComponents.values.map { it.getRecipeProgressBarDouble(machine, recipe) }.firstOrNull { it != null }?.let {
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