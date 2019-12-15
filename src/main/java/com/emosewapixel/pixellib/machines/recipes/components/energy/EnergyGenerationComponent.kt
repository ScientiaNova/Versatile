package com.emosewapixel.pixellib.machines.recipes.components.energy

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.implementations.energy.TEEnergyOutputProperty
import com.emosewapixel.pixellib.machines.properties.implementations.processing.handlers.EnergyOutputProcessingHandler
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponent
import com.emosewapixel.pixellib.machines.recipes.components.grouping.RecipeComponentFamilies
import com.emosewapixel.pixellib.machines.recipes.components.stats.TimeComponent
import net.minecraft.util.text.TranslationTextComponent

class EnergyGenerationComponent(val maxPerTick: Int) : IRecipeComponent<Int> {
    override val name = "energy_generation"
    override val family = RecipeComponentFamilies.STATS

    override fun isRecipeValid(recipe: Recipe) = recipe[this]?.value?.let { it in 0..maxPerTick } ?: false

    override fun addDefaultProperty(te: BaseTileEntity, properties: MutableList<ITEBoundProperty>) {
        properties += TEEnergyOutputProperty(maxPerTick, "energyOutput", te)
    }

    override fun addExtraInfo(): List<(Recipe) -> String> = listOf({ recipe ->
        TranslationTextComponent("extra_recipe_info.energy_generated_per_tick", recipe[this]?.value ?: 0).string
    }, { recipe ->
        TranslationTextComponent("extra_recipe_info.total_energy_generated", (recipe[this]?.value
                ?: 0) * (recipe[TimeComponent::class.java]?.value ?: 0)).string
    })

    override fun getProcessingHandler(machine: BaseTileEntity) = (machine.teProperties["energyOutput"] as? TEEnergyOutputProperty)?.let {
        EnergyOutputProcessingHandler(it)
    }
}