package com.EmosewaPixel.pixellib.materialsystem.lists;

import com.EmosewaPixel.pixellib.materialsystem.types.ObjectType;

import java.util.Collection;
import java.util.HashMap;

//This class contains functions for interacting with the global list of object types
public class ObjTypes {
    private static HashMap<String, ObjectType> objTypes = new HashMap<>();

    public static void add(ObjectType type) {
        if (objTypes.get(type.getName()) != null)
            objTypes.get(type.getName()).merge(type);
        else
            objTypes.put(type.getName(), type);
    }

    public static ObjectType get(String name) {
        return objTypes.get(name);
    }

    public static Collection<ObjectType> getAll() {
        return objTypes.values();
    }
}