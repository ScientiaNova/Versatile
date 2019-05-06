package com.EmosewaPixel.pixellib.recipes;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRecipeList<T extends SimpleMachineRecipe, B extends AbstractRecipeBuilder<T, B>> {
    private ArrayList<T> recipes = new ArrayList<>();
    private int maxInputs;
    private int maxOutputs;

    public AbstractRecipeList(int maxInputs, int maxOutputs) {
        this.maxInputs = maxInputs;
        this.maxOutputs = maxOutputs;
    }

    public void add(T recipe) {
        recipes.add(recipe);
    }

    public abstract B recipeBuilder();

    public int getMaxInputs() {
        return maxInputs;
    }

    public int getMaxOutputs() {
        return maxOutputs;
    }

    public List<T> getReipes() {
        return recipes;
    }
}
