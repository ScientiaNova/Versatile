package com.emosewapixel.pixellib.machines.recipes.builders

import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.gui.textures.updating.ProgressBar
import com.emosewapixel.pixellib.machines.recipes.AutomationRecipeList
import com.emosewapixel.pixellib.machines.recipes.RecipeList
import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponent
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidInputsComponent
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidOutputsComponent
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemInputsComponent
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemOutputsComponent
import com.emosewapixel.pixellib.machines.recipes.components.stats.EnergyPerTickComponent
import com.emosewapixel.pixellib.machines.recipes.components.stats.TimeComponent
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

    fun energyPerTick(max: Int): StandardRecipeListBuilder {
        components += EnergyPerTickComponent(max)
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