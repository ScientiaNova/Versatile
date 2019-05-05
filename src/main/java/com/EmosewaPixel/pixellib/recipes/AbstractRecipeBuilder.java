package com.EmosewaPixel.pixellib.recipes;

import java.util.List;

public abstract class AbstractRecipeBuilder<T extends SimpleMachineRecipe, R extends AbstractRecipeBuilder<T, R>> {
    private AbstractRecipeList<T, R> recipeList;
    private List<Object> inputs;
    private List<Object> outputs;
    private int time;

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
        for (Object input : inputs)
            this.inputs.add(input);
        return (R) this;
    }

    public R output(Object... outputs) {
        for (Object output : outputs)
            this.outputs.add(output);
        return (R) this;
    }

    public R time(int amount) {
        time = amount;
        return (R) this;
    }

    public abstract T build();

    public void buildAndRegister() {
        recipeList.add(build());
    }
}
