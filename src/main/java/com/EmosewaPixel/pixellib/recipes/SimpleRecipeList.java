package com.EmosewaPixel.pixellib.recipes;

public class SimpleRecipeList extends AbstractRecipeList<SimpleMachineRecipe, SimpleRecipeBuilder> {
    public SimpleRecipeList(int maxInputs, int maxOutputs) {
        super(maxInputs, maxOutputs);
    }

    @Override
    public SimpleRecipeBuilder recipeBuilder() {
        return new SimpleRecipeBuilder(this);
    }
}
