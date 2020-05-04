package com.scientianova.versatile.resources

import com.scientianovateam.versatile.common.extensions.JsonBuilder
import com.scientianovateam.versatile.common.extensions.toResLoc
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
    fun addAssetsJSON(location: ResourceLocation, json: JsonBuilder.() -> Unit) = addDataJSON(location, com.scientianovateam.versatile.common.extensions.json(json))

    @JvmStatic
    fun addAssetsJSON(location: String, json: JsonBuilder.() -> Unit) = addAssetsJSON(location.toResLoc(), json)

    @JvmStatic
    fun addDataJSON(location: ResourceLocation, json: JsonElement) {
        DATA[location] = json
    }

    @JvmStatic
    fun addDataJSON(location: ResourceLocation, json: JsonBuilder.() -> Unit) = addDataJSON(location, com.scientianovateam.versatile.common.extensions.json(json))

    @JvmStatic
    fun addDataJSON(location: String, json: JsonBuilder.() -> Unit) = addDataJSON(location.toResLoc(), json)
}