@file:JvmName("JSONAdder")

package com.scientianova.versatile.resources

import com.google.gson.JsonElement
import com.scientianova.versatile.common.extensions.JsonBuilder
import com.scientianova.versatile.common.extensions.toResLoc
import net.minecraft.util.ResourceLocation

val ASSETS = hashMapOf<ResourceLocation, JsonElement>()
val DATA = hashMapOf<ResourceLocation, JsonElement>()

fun addAssetsJSON(location: ResourceLocation, json: JsonElement) {
    ASSETS[location] = json
}

fun addAssetsJSON(location: ResourceLocation, json: JsonBuilder.() -> Unit) = addDataJSON(location, com.scientianova.versatile.common.extensions.json(json))
fun addAssetsJSON(location: String, json: JsonBuilder.() -> Unit) = addAssetsJSON(location.toResLoc(), json)

fun addDataJSON(location: ResourceLocation, json: JsonElement) {
    DATA[location] = json
}

fun addDataJSON(location: ResourceLocation, json: JsonBuilder.() -> Unit) = addDataJSON(location, com.scientianova.versatile.common.extensions.json(json))
fun addDataJSON(location: String, json: JsonBuilder.() -> Unit) = addDataJSON(location.toResLoc(), json)