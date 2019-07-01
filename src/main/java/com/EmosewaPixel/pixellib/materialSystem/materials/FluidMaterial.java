package com.EmosewaPixel.pixellib.materialSystem.materials;

import com.EmosewaPixel.pixellib.materialSystem.MaterialRegistry;
import com.EmosewaPixel.pixellib.materialSystem.element.Element;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;

//Fluid Materials are materials meant only for generating fluids of. NYI as forge hasn't sorted fluids out yet
public class FluidMaterial extends Material {
    private int temperature = 373;
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

    public FluidMaterial setElement(Element element) {
        super.setElement(element);
        return this;
    }

    public FluidMaterial setCompoundType(CompoundType type) {
        super.setCompoundType(type);
        return this;
    }

    public FluidMaterial setTemperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public FluidMaterial setBoilingMaterial(FluidMaterial boilingMaterial) {
        if (!this.hasTag(MaterialRegistry.IS_GAS))
            this.boilingMaterial = boilingMaterial;
        return this;
    }

    public int getTemperature() {
        return temperature;
    }

    public FluidMaterial getBoilingMaterial() {
        return boilingMaterial;
    }

    public void merge(FluidMaterial mat) {
        super.merge(mat);
        if (mat.getTemperature() != 373)
            temperature = mat.getTemperature();
        if (mat.getBoilingMaterial() != null)
            boilingMaterial = mat.getBoilingMaterial();
    }
}
