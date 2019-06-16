package com.EmosewaPixel.pixellib.recipes;

//This is a builder for Simple Machine Recipes
public class SimpleRecipeBuilder extends AbstractRecipeBuilder<SimpleMachineRecipe, SimpleRecipeBuilder> {
    public SimpleRecipeBuilder(AbstractRecipeList<SimpleMachineRecipe, SimpleRecipeBuilder> list) {
        super(list);
    }

    @Override
    public SimpleMachineRecipe build() {
        if (getInputs().size() <= getRecipeList().getMaxInputs() && getOutputs().size() <= getRecipeList().getMaxOutputs())
            return new SimpleMachineRecipe(getInputs().toArray(), getOutputs().toArray(), getTime());
        return SimpleMachineRecipe.EMPTY;
    }
}