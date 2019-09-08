package com.emosewapixel.pixellib.resources

import com.google.gson.JsonElement
import net.minecraft.util.ResourceLocation

//This class contains functions that mods can use to add JSONs to the fake data and resource pack
object JSONAdder {
    @JvmField
    val ASSETS = hashMapOf<ResourceLocation, JsonElement>()

    @JvmField
    val DATA = hashMapOf<ResourceLocation, JsonElement>()

    @JvmStatic
    fun addAssetsJSON(location: ResourceLocation, json: JsonElement) {
        ASSETS[location] = json
    }

    @JvmStatic
    fun addDataJSON(location: ResourceLocation, json: JsonElement) {
        DATA[location] = json
    }
}