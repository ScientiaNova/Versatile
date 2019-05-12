package com.EmosewaPixel.pixellib.recipes;

import net.minecraft.util.ResourceLocation;

public class EnergyRecipeList extends AbstractRecipeList<EnergyMachineRecipe, EnergyRecipeBuilder> {
    public EnergyRecipeList(ResourceLocation name, int maxInputs, int maxOutputs) {
        super(name, maxInputs, maxOutputs);
    }

    @Override
    public EnergyRecipeBuilder recipeBuilder() {
        return new EnergyRecipeBuilder(this);
    }
}