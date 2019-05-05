package com.EmosewaPixel.pixellib.recipes;

public class EnergyRecipeBuilder extends AbstractRecipeBuilder<EnergyMachineRecipe, EnergyRecipeBuilder> {
    private int energyPerTick;

    public EnergyRecipeBuilder(AbstractRecipeList<EnergyMachineRecipe, EnergyRecipeBuilder> list) {
        super(list);
    }

    public EnergyRecipeBuilder energyPerTick(int energy) {
        energyPerTick = energy;
        return this;
    }

    protected int getEnergyPerTick() {
        return energyPerTick;
    }

    @Override
    public EnergyMachineRecipe build() {
        if (getInputs().size() <= getRecipeList().getMaxInputs() && getOutputs().size() <= getRecipeList().getMaxOutputs())
            return new EnergyMachineRecipe(getInputs().toArray(), getOutputs().toArray(), getTime(), energyPerTick);
        return EnergyMachineRecipe.EMPTY;
    }
}
