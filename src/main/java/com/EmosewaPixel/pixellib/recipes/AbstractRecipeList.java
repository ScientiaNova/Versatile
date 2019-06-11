package com.EmosewaPixel.pixellib.recipes;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRecipeList<T extends SimpleMachineRecipe, B extends AbstractRecipeBuilder<T, B>> {
    private ArrayList<T> recipes = new ArrayList<>();
    private ResourceLocation name;
    private int maxInputs;
    private int maxOutputs;

    public AbstractRecipeList(ResourceLocation name, int maxInputs, int maxOutputs) {
        this.name = name;
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

    public int getMaxRecipeSlots() {
        return maxInputs + maxOutputs;
    }

    public List<T> getReipes() {
        return recipes;
    }

    public ResourceLocation getName() {
        return name;
    }
}