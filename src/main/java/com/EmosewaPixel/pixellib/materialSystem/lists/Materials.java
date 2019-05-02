package com.EmosewaPixel.pixellib.materialSystem.lists;

import com.EmosewaPixel.pixellib.materialSystem.materials.Material;

import java.util.Collection;
import java.util.HashMap;

public class Materials {
    private static HashMap<String, Material> materials = new HashMap<>();

    public static void add(Material mat) {
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
