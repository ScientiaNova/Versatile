package com.emosewapixel.pixellib.machines.recipes

import net.minecraft.util.ResourceLocation

//This is a Recipe List for Energy Machine Recipes
class EnergyRecipeList(name: ResourceLocation, maxInputs: Int, maxFluidInputs: Int, maxOutputs: Int, maxFluidOutputs: Int) : AbstractRecipeList<EnergyMachineRecipe, EnergyRecipeBuilder>(name, maxInputs, maxFluidInputs, maxOutputs, maxFluidOutputs) {
    override fun recipeBuilder() = EnergyRecipeBuilder(this)
    override fun build(dsl: EnergyRecipeBuilder.() -> Unit) {
        val builder = EnergyRecipeBuilder(this)
        builder.dsl()
        builder.buildAndRegister()
    }
}