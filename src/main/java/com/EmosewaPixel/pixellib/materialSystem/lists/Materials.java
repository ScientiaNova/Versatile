package com.EmosewaPixel.pixellib.materialsystem.lists;

import com.EmosewaPixel.pixellib.materialsystem.materials.Material;

import java.util.Collection;
import java.util.HashMap;

//This class contains functions for interacting with the global list of materials
public final class Materials {
    private static final HashMap<String, Material> materials = new HashMap<>();

    public static void add(Material mat) {
        if (materials.get(mat.getName()) != null)
            materials.get(mat.getName()).merge(mat);
        else
            materials.put(mat.getName(), mat);
    }

    public static Material get(String name) {
        return materials.get(name);
    }

    public static boolean exists(String name) {
        return get(name) != null;
    }

    public static Collection<Material> getAll() {
        return materials.values();
    }
}
