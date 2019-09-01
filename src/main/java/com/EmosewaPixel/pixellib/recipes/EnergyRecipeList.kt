package com.EmosewaPixel.pixellib.recipes

import net.minecraft.util.ResourceLocation

//This is a Recipe List for Energy Machine Recipes
class EnergyRecipeList(name: ResourceLocation, maxInputs: Int, maxOutputs: Int) : AbstractRecipeList<EnergyMachineRecipe, EnergyRecipeBuilder>(name, maxInputs, maxOutputs) {
    override fun recipeBuilder() = EnergyRecipeBuilder(this)
}