package com.EmosewaPixel.pixellib.materialSystem.types;

import com.EmosewaPixel.pixellib.materialSystem.lists.TextureTypes;

//Texture Types determine which sets of textures Material use for Object Types
public class TextureType {
    private String name;

    public TextureType(String name) {
        this.name = name;
        TextureTypes.add(this);
    }

    public String toString() {
        return name;
    }
}
