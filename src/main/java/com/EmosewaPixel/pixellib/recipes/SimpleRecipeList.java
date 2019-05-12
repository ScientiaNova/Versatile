package com.EmosewaPixel.pixellib.recipes;

import net.minecraft.util.ResourceLocation;

public class SimpleRecipeList extends AbstractRecipeList<SimpleMachineRecipe, SimpleRecipeBuilder> {
    public SimpleRecipeList(ResourceLocation name, int maxInputs, int maxOutputs) {
        super(name, maxInputs, maxOutputs);
    }

    @Override
    public SimpleRecipeBuilder recipeBuilder() {
        return new SimpleRecipeBuilder(this);
    }
}
