package com.scientianovateam.versatile.recipes.components.energy.generation

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getPrimitiveOrNull
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.properties.implementations.energy.TEEnergyOutputProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.handlers.EnergyOutputProcessingHandler
import com.scientianovateam.versatile.recipes.Recipe
import com.scientianovateam.versatile.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.recipes.components.grouping.RecipeComponentFamilies
import com.scientianovateam.versatile.recipes.components.time.TimeComponent
import net.minecraft.util.text.TranslationTextComponent

data class EnergyGenerationComponent(val min: Int, val max: Int) : IRecipeComponent<Int> {
    override val name = "energyGeneration"
    override val family = RecipeComponentFamilies.STATS
    override val serializer = Serializer

    override fun isRecipeValid(recipe: Recipe) = recipe[this]?.value?.let { it in min..max } ?: false

    override fun addDefaultProperty(te: BaseTileEntity, properties: MutableList<ITEBoundProperty>) {
        properties += TEEnergyOutputProperty(max, "energyOutput", te)
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

    object Serializer : IRegisterableJSONSerializer<EnergyGenerationComponent, JsonObject> {
        override val registryName = "energy_generation".toResLocV()

        override fun read(json: JsonObject) = EnergyGenerationComponent(
                json.getPrimitiveOrNull("min")?.asInt ?: 0,
                json.getPrimitiveOrNull("max")?.asInt ?: Int.MAX_VALUE
        )

        override fun write(obj: EnergyGenerationComponent) = json {
            "min" to obj.min
            "max" to obj.max
        }
    }
}