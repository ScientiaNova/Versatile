package com.scientianovateam.versatile.recipes.components.energy.consumption

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getIntOrNull
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.properties.implementations.energy.TEEnergyInputProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.handlers.EnergyInputProcessingHandler
import com.scientianovateam.versatile.recipes.Recipe
import com.scientianovateam.versatile.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.recipes.components.grouping.RecipeComponentFamilies
import com.scientianovateam.versatile.recipes.components.time.TimeComponent
import net.minecraft.util.text.TranslationTextComponent

data class EnergyConsumptionComponent(val min: Int, val max: Int) : IRecipeComponent<Int> {
    override val name = "energyConsumption"
    override val family = RecipeComponentFamilies.STATS
    override val serializer = Serializer

    override fun isRecipeValid(recipe: Recipe) = recipe[this]?.value?.let { it in min..max } ?: false

    override fun addDefaultProperty(te: BaseTileEntity, properties: MutableList<ITEBoundProperty>) {
        properties += TEEnergyInputProperty(max, "energyInput", te)
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

    object Serializer : IRegisterableJSONSerializer<EnergyConsumptionComponent, JsonObject> {
        override val registryName = "energy_consumption".toResLocV()

        override fun read(json: JsonObject) = EnergyConsumptionComponent(
                json.getIntOrNull("min") ?: 0,
                json.getIntOrNull("max") ?: Int.MAX_VALUE
        )

        override fun write(obj: EnergyConsumptionComponent) = json {
            "min" to obj.min
            "max" to obj.max
        }
    }
}