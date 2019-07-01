package com.EmosewaPixel.pixellib.recipes;

//Energy Machine Recipes are Machine Recipes that take power
public class EnergyMachineRecipe extends SimpleMachineRecipe {
    private int energyPerTick;
    public static EnergyMachineRecipe EMPTY = new EnergyMachineRecipe(new Object[0], new Float[0], new Object[0], new Float[0], 0, 0);

    public EnergyMachineRecipe(Object[] input, Float[] consumeChances, Object[] output, Float[] outputChances, int time, int energyperTick) {
        super(input, consumeChances, output, outputChances, time);
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
