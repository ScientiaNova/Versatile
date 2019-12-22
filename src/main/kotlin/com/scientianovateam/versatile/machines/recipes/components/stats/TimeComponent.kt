package com.scientianovateam.versatile.machines.recipes.components.stats

import com.scientianovateam.versatile.common.extensions.shorten
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.properties.implementations.primitives.doubles.IncrementingDoubleProperty
import com.scientianovateam.versatile.machines.properties.implementations.primitives.doubles.IntegerBasedDoubleProperty
import com.scientianovateam.versatile.machines.properties.implementations.primitives.doubles.RecipeTimeBasedDoubleProperty
import com.scientianovateam.versatile.machines.properties.implementations.primitives.integers.TEClearableIntegerProperty
import com.scientianovateam.versatile.machines.properties.implementations.primitives.integers.TEIntegerProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.handlers.TimeProcessingHandler
import com.scientianovateam.versatile.machines.properties.implementations.recipes.RecipeProperty
import com.scientianovateam.versatile.machines.recipes.Recipe
import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.machines.recipes.components.grouping.RecipeComponentFamilies
import net.minecraft.util.text.TranslationTextComponent

object TimeComponent : IRecipeComponent<Int> {
    override val name = "time"
    override val family = RecipeComponentFamilies.STATS

    override fun isRecipeValid(recipe: Recipe) = recipe[this]?.value?.let { it > 0 } ?: false

    override fun addDefaultProperty(te: BaseTileEntity, properties: MutableList<ITEBoundProperty>) {
        properties += TEClearableIntegerProperty("processingTime", te)
    }

    override fun addExtraInfo() = listOf { recipe: Recipe ->
        TranslationTextComponent("extra_recipe_info.duration", ((recipe[this]?.value ?: 0) / 20.0).shorten()).string
    }

    override fun getProgressBarDouble(machine: BaseTileEntity?) = machine?.let {
        val recipeProperty = it.teProperties["recipe"] as? RecipeProperty ?: return null
        val timeProperty = it.teProperties["processingTime"] as? TEIntegerProperty ?: return null
        RecipeTimeBasedDoubleProperty(timeProperty, recipeProperty)
    }

    override fun getRecipeProgressBarDouble(machine: BaseTileEntity?, recipe: Recipe) = machine?.let {
        val timeProperty = it.teProperties["processingTime"] as? TEIntegerProperty ?: return null
        val recipeTime = recipe[this] ?: return null
        IntegerBasedDoubleProperty(timeProperty, recipeTime.value)
    } ?: recipe[this]?.let { recipeTime ->
        IncrementingDoubleProperty(1.0 / recipeTime.value)
    }

    override fun getProcessingHandler(machine: BaseTileEntity) = (machine.teProperties["processingTime"] as? TEIntegerProperty)?.let {
        TimeProcessingHandler(it)
    }
}