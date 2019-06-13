package com.EmosewaPixel.pixellib.materialSystem.materials;

import com.EmosewaPixel.pixellib.materialSystem.MaterialRegistry;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;

public class FluidMaterial extends Material {
    ;
    private FluidMaterial boilingMaterial = null;

    public FluidMaterial(String name, TextureType textureType, int color, int tier) {
        super(name, textureType, color, tier);
    }

    public FluidMaterial(String name, TextureType textureType, int color) {
        this(name, textureType, color, 0);
    }

    public FluidMaterial addTags(String... tags) {
        super.addTags(tags);
        return this;
    }

    public FluidMaterial blacklistTypes(ObjectType... types) {
        super.blacklistTypes(types);
        return this;
    }

    public FluidMaterial setComposition(MaterialStack... stacks) {
        super.setComposition(stacks);
        return this;
    }

    public FluidMaterial setBoilingMaterial(FluidMaterial boilingMaterial) {
        if (!this.hasTag(MaterialRegistry.IS_GAS))
            this.boilingMaterial = boilingMaterial;
        return this;
    }
}