package com.scientianovateam.versatile.machines.recipes.components.energy.consumption

import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.properties.implementations.energy.TEEnergyInputProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.handlers.EnergyInputProcessingHandler
import com.scientianovateam.versatile.machines.recipes.Recipe
import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.machines.recipes.components.grouping.RecipeComponentFamilies
import com.scientianovateam.versatile.machines.recipes.components.time.TimeComponent
import net.minecraft.util.text.TranslationTextComponent

class EnergyConsumptionComponent(val maxPerTick: Int) : IRecipeComponent<Int> {
    override val name = "energy_consumption"
    override val family = RecipeComponentFamilies.STATS

    override fun isRecipeValid(recipe: Recipe) = recipe[this]?.value?.let { it in 0..maxPerTick } ?: false

    override fun addDefaultProperty(te: BaseTileEntity, properties: MutableList<ITEBoundProperty>) {
        properties += TEEnergyInputProperty(maxPerTick, "energyInput", te)
    }

    override fun addExtraInfo(): List<(Recipe) -> String> = listOf({ recipe ->
        TranslationTextComponent("extra_recipe_info.energy_consumed_per_tick", recipe[this]?.value ?: 0).string
    }, { recipe ->
        TranslationTextComponent("extra_recipe_info.total_energy_consumed", (recipe[this]?.value
                ?: 0) * (recipe[TimeComponent::class.java]?.value ?: 0)).string
    })

    override fun getProcessingHandler(machine: BaseTileEntity) = (machine.teProperties["energyInput"] as? TEEnergyInputProperty)?.let {
        EnergyInputProcessingHandler(it)
    }
}