package com.EmosewaPixel.pixellib.resourceAddition;

import com.google.gson.JsonElement;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class JSONAdder {
    protected static HashMap<ResourceLocation, JsonElement> ASSETS = new HashMap<>();
    protected static HashMap<ResourceLocation, JsonElement> DATA = new HashMap<>();

    public static void addAssetsJSON(ResourceLocation location, JsonElement json) {
        ASSETS.put(location, json);
    }

    public static void addDataJSON(ResourceLocation location, JsonElement json) {
        DATA.put(location, json);
    }
}