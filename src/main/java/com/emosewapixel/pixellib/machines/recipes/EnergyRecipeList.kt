package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.extensions.shorten
import com.emosewapixel.pixellib.machines.gui.layout.GUIPage
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.gui.textures.ProgressBar
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

//This is a Recipe List for Energy Machine Recipes
class EnergyRecipeList @JvmOverloads constructor(
        name: ResourceLocation,
        maxInputs: Int = 0,
        maxFluidInputs: Int = 0,
        maxOutputs: Int = 0,
        maxFluidOutputs: Int = 0,
        progressBar: ProgressBar = BaseTextures.ARROW_BAR
) : AbstractRecipeList<EnergyMachineRecipe, EnergyRecipeBuilder>(name, maxInputs, maxFluidInputs, maxOutputs, maxFluidOutputs, progressBar) {
    override fun recipeBuilder() = EnergyRecipeBuilder(this)
    override fun build(dsl: EnergyRecipeBuilder.() -> Unit) {
        val builder = EnergyRecipeBuilder(this)
        builder.dsl()
        builder.buildAndRegister()
    }

    override val extraTextRows = 3

    @OnlyIn(Dist.CLIENT)
    override fun renderExtraInfo(page: GUIPage, recipe: EnergyMachineRecipe) {
        val fontRenderer = Minecraft.getInstance().fontRenderer

        val totalEnergy = TranslationTextComponent("extra_recipe_info.total_energy", recipe.energyPerTick * recipe.time).string
        fontRenderer.drawString(totalEnergy, 0f, page.height - 28f, 0x404040)

        val energyPerTick = TranslationTextComponent("extra_recipe_info.energy_per_tick", recipe.energyPerTick).string
        fontRenderer.drawString(energyPerTick, 0f, page.height - 18f, 0x404040)

        val time = TranslationTextComponent("extra_recipe_info.duration", (recipe.time / 20.0).shorten()).string
        fontRenderer.drawString(time, 0f, page.height - 8f, 0x404040)
    }
}