package com.EmosewaPixel.pixellib.materialsystem.lists;

import com.EmosewaPixel.pixellib.materialsystem.types.TextureType;

import java.util.Collection;
import java.util.HashMap;

//This class contains functions for interacting with the global list of texture types
public final class TextureTypes {
    public static final HashMap<String, TextureType> textureTypes = new HashMap<>();

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