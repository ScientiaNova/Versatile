package com.EmosewaPixel.pixellib.materialSystem.lists;

import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;

import java.util.Collection;
import java.util.HashMap;

public class ObjTypes {
    private static HashMap<String, ObjectType> objTypes = new HashMap<>();

    public static void add(ObjectType type) {
        objTypes.put(type.getName(), type);
    }

    public static ObjectType get(String name) {
        return objTypes.get(name);
    }

    public static Collection<ObjectType> getAll() {
        return objTypes.values();
    }
}