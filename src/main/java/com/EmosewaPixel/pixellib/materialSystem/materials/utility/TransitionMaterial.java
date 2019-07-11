package com.EmosewaPixel.pixellib.materialsystem.materials.utility;

import com.EmosewaPixel.pixellib.materialsystem.materials.Material;

import java.util.function.Supplier;

/*
Transition Materials are materials that can't exist in a regular state and when used in recipes turn in another Material and as such they can't be
used for generating items/blocks/fluids.
For example, if you're generating decomposition recipes and a material contains Hydrogen (H), you'd want to make a recipe that gives Hydrogen Gas(H2)
and as such you'd have the Hydrogen replaced with Hydrogen Gas and every other material stack in the recipe would be multiplied by the needed amount for that conversion
 */

public class TransitionMaterial extends Material {
    private Supplier<Material> endMaterial;
    private int neededAmount;

    public TransitionMaterial(String name, Supplier<Material> becomes, int neededAmount) {
        super(name, null, -1, -1);
        endMaterial = becomes;
        this.neededAmount = neededAmount;
    }

    public Material getEndMaterial() {
        return endMaterial.get();
    }

    public int getNeededAmount() {
        return neededAmount;
    }
}