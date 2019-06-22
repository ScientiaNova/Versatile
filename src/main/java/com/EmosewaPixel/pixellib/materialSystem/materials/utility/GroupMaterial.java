package com.EmosewaPixel.pixellib.materialSystem.materials.utility;

import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.materials.MaterialStack;

/*
Group Materials are a way of grouping together materials. They can't be used for generating items/blocks/fluids and they're only meant to be used as components
for other materials.
Generally they're meant to be used for chemical groups that can't exist by themselves and would be broken down to other materials in recipes.
For example, you'd make a Hydroxide Group consisting of Oxygen and Hydrogen, a Sulfate Group consisting of Sulfur Dioxide and Oxygen Gas,
a Carbonate Group consisting of Carbon Monoxide and Oxygen Gas, etc.
Using a group instead of a group components in a material's composition lets mods like Formula Tooltips properly display the composition
 */
public class GroupMaterial extends Material {
    public GroupMaterial(String name) {
        super(name, null, -1, -1);
    }

    public GroupMaterial setComposition(MaterialStack... stacks) {
        super.setComposition(stacks);
        return this;
    }
}