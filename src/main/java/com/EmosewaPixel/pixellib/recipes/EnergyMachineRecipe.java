package com.EmosewaPixel.pixellib.recipes;

//Energy Machine Recipes are Machine Recipes that take power
public class EnergyMachineRecipe extends SimpleMachineRecipe {
    private int energyPerTick;
    public static EnergyMachineRecipe EMPTY = new EnergyMachineRecipe(null, null, 0, 0);

    public EnergyMachineRecipe(Object[] input, Object[] output, int time, int energyperTick) {
        super(input, output, time);
        this.energyPerTick = energyperTick;
    }

    @Override
    public boolean isEmpty() {
        return this == EMPTY;
    }

    public int getEnergyPerTick() {
        return energyPerTick;
    }

    public void setEnergyPerTick(int energyPerTick) {
        this.energyPerTick = energyPerTick;
    }
}
