package com.emosewapixel.pixellib.machines.recipes.components

import com.emosewapixel.pixellib.extensions.shorten
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.implementations.primitives.TEIntegerProperty
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.components.grouping.RecipeComponentFamilies
import net.minecraft.util.text.TranslationTextComponent

object TimeComponent : IRecipeComponent<Int> {
    override val name = "time"
    override val family = RecipeComponentFamilies.STATS

    override fun isRecipeValid(recipe: Recipe) = recipe[this]?.value?.let { it > 0 } ?: false

    override fun addDefaultProperty(te: BaseTileEntity, properties: MutableList<ITEBoundProperty>) {
        properties += TEIntegerProperty("processingTime", te)
    }

    override fun addExtraInfo() = listOf { recipe: Recipe -> TranslationTextComponent("extra_recipe_info.duration", ((recipe[this]?.value ?: 0) / 20.0).shorten()).string }
}