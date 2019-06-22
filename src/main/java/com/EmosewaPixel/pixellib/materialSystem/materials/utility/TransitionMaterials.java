package com.EmosewaPixel.pixellib.materialSystem.materials.utility;

import com.EmosewaPixel.pixellib.materialSystem.materials.Material;

/*
Transition Materials are materials that can't exist in a regular state and when used in recipes turn in another Material and as such they can't be
used for generating items/blocks/fluids.
For example, if you're generating decomposition recipes and a material contains Hydrogen (H), you'd want to make a recipe that gives Hydrogen Gas(H2)
and as such you'd have the Hydrogen replaced with Hydrogen Gas and every other material stack in the recipe would be multiplied by the needed amount for that conversion
 */

public class TransitionMaterials extends Material {
    private Material endMaterial;
    private int neededAmount;

    public TransitionMaterials(String name, Material becomes, int neededAmount) {
        super(name, null, -1, -1);
        endMaterial = becomes;
        this.neededAmount = neededAmount;
    }

    public Material getEndMaterial() {
        return endMaterial;
    }

    public int getNeededAmount() {
        return neededAmount;
    }
}
