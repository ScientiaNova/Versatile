package com.scientianovateam.versatile.recipes.builders

import com.scientianovateam.versatile.machines.gui.textures.BaseTextures
import com.scientianovateam.versatile.machines.gui.textures.updating.ProgressBar
import com.scientianovateam.versatile.recipes.lists.AutomationRecipeList
import com.scientianovateam.versatile.recipes.lists.RecipeList
import com.scientianovateam.versatile.recipes.components.IRecipeComponent
import com.scientianovateam.versatile.recipes.components.ingredients.fluids.input.FluidInputsComponent
import com.scientianovateam.versatile.recipes.components.ingredients.fluids.output.FluidOutputsComponent
import com.scientianovateam.versatile.recipes.components.ingredients.items.input.ItemInputsComponent
import com.scientianovateam.versatile.recipes.components.ingredients.items.output.ItemOutputsComponent
import com.scientianovateam.versatile.recipes.components.energy.consumption.EnergyConsumptionComponent
import com.scientianovateam.versatile.recipes.components.energy.generation.EnergyGenerationComponent
import com.scientianovateam.versatile.recipes.components.time.TimeComponent
import net.minecraft.util.ResourceLocation

open class StandardRecipeListBuilder(protected val name: ResourceLocation) {
    protected var progressBar: ProgressBar = BaseTextures.ARROW_BAR
    protected var genJEIPage: Boolean = true
    protected val components = mutableListOf<IRecipeComponent<*>>()

    fun progressBar(bar: ProgressBar): StandardRecipeListBuilder {
        progressBar = bar
        return this
    }

    fun genJEIPage(bool: Boolean): StandardRecipeListBuilder {
        genJEIPage = bool
        return this
    }

    fun time(): StandardRecipeListBuilder {
        components += TimeComponent
        return this
    }

    fun consumeEnergy(maxPerTick: Int): StandardRecipeListBuilder {
        components += EnergyConsumptionComponent(maxPerTick)
        return this
    }

    fun generateEnergy(maxPerTick: Int): StandardRecipeListBuilder {
        components += EnergyGenerationComponent(maxPerTick)
        return this
    }

    @JvmOverloads
    fun itemInputs(max: Int, min: Int = 0): StandardRecipeListBuilder {
        components += ItemInputsComponent(max, min)
        return this
    }

    @JvmOverloads
    fun itemOutputs(max: Int, min: Int = 0): StandardRecipeListBuilder {
        components += ItemOutputsComponent(max, min)
        return this
    }

    @JvmOverloads
    fun fluidInputs(max: Int, min: Int = 0): StandardRecipeListBuilder {
        components += FluidInputsComponent(max, min)
        return this
    }

    @JvmOverloads
    fun fluidOutputs(max: Int, min: Int = 0): StandardRecipeListBuilder {
        components += FluidOutputsComponent(max, min)
        return this
    }

    open fun build() = RecipeList(name, *components.toTypedArray(), progressBar = progressBar, genJEIPage = genJEIPage)
}

open class AutomationRecipeListBuilder(name: ResourceLocation): StandardRecipeListBuilder(name) {
    override fun build() = AutomationRecipeList(name, *components.toTypedArray(), progressBar = progressBar, genJEIPage = genJEIPage)
}