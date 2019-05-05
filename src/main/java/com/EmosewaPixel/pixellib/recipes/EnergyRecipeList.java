package com.EmosewaPixel.pixellib.recipes;

public class EnergyRecipeList extends AbstractRecipeList<EnergyMachineRecipe, EnergyRecipeBuilder> {
    public EnergyRecipeList(int maxInputs, int maxOutputs) {
        super(maxInputs, maxOutputs);
    }

    @Override
    public EnergyRecipeBuilder recipeBuilder() {
        return new EnergyRecipeBuilder(this);
    }
}