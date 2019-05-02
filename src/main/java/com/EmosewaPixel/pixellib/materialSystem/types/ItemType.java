package com.EmosewaPixel.pixellib.materialSystem.types;

import com.EmosewaPixel.pixellib.materialSystem.materials.Material;

import java.util.function.Predicate;

public class ItemType extends ObjectType {
    public ItemType(String name, Predicate<Material> requirement) {
        super(name, requirement);
    }
}
