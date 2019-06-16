package com.EmosewaPixel.pixellib.recipes;

import net.minecraft.util.ResourceLocation;

//This is a Recipe List for Energy Machine Recipes
public class EnergyRecipeList extends AbstractRecipeList<EnergyMachineRecipe, EnergyRecipeBuilder> {
    public EnergyRecipeList(ResourceLocation name, int maxInputs, int maxOutputs) {
        super(name, maxInputs, maxOutputs);
    }

    @Override
    public EnergyRecipeBuilder recipeBuilder() {
        return new EnergyRecipeBuilder(this);
    }
}