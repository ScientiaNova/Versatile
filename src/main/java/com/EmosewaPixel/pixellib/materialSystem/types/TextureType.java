package com.EmosewaPixel.pixellib.materialSystem.types;

import com.EmosewaPixel.pixellib.materialSystem.lists.TextureTypes;

public class TextureType {
    private String name;

    public TextureType(String name) {
        this.name = name;
        TextureTypes.textureTypes.add(this);
    }

    public String toString() {
        return name;
    }
}
