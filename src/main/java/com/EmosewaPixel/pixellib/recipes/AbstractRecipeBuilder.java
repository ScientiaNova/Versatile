package com.EmosewaPixel.pixellib.recipes;

import com.EmosewaPixel.pixellib.PixelLib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractRecipeBuilder<T extends SimpleMachineRecipe, R extends AbstractRecipeBuilder<T, R>> {
    private AbstractRecipeList<T, R> recipeList;
    private List<Object> inputs = new ArrayList<>();
    private List<Object> outputs = new ArrayList<>();
    private int time = 0;

    public AbstractRecipeBuilder(AbstractRecipeList<T, R> recipeList) {
        this.recipeList = recipeList;
    }

    protected List<Object> getInputs() {
        return inputs;
    }

    protected List<Object> getOutputs() {
        return outputs;
    }

    protected int getTime() {
        return time;
    }

    protected AbstractRecipeList<T, R> getRecipeList() {
        return recipeList;
    }

    public R input(Object... inputs) {
        this.inputs.addAll(Arrays.asList(inputs));
        return (R) this;
    }

    public R output(Object... outputs) {
        this.outputs.addAll(Arrays.asList((outputs)));
        return (R) this;
    }

    public R time(int amount) {
        time = amount;
        return (R) this;
    }

    public abstract T build();

    public void buildAndRegister() {
        if (!build().isEmpty())
            recipeList.add(build());
        else
            PixelLib.LOGGER.error("Recipe with output {} is empty", outputs);
    }
}
