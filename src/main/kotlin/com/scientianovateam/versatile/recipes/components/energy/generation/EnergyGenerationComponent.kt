package com.scientianovateam.versatile.recipes.components.energy.generation

import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.properties.implementations.energy.TEEnergyOutputProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.handlers.EnergyOutputProcessingHandler
import com.scientianovateam.versatile.recipes.Recipe
import com.scientianovateam.versatile.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.recipes.components.grouping.RecipeComponentFamilies
import com.scientianovateam.versatile.recipes.components.time.TimeComponent
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