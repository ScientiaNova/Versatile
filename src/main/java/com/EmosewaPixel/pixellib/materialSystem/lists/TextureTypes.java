package com.EmosewaPixel.pixellib.materialSystem.lists;

import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;

import java.util.Collection;
import java.util.HashMap;

public class TextureTypes {
    public static HashMap<String, TextureType> textureTypes = new HashMap<>();

    public static void add(TextureType type) {
        textureTypes.put(type.toString(), type);
    }

    public static TextureType get(String name) {
        return textureTypes.get(name);
    }

    public static Collection<TextureType> getAll() {
        return textureTypes.values();
    }
}