@file:JvmName("JSONAdder")

package com.scientianova.versatile.resources

import com.google.gson.JsonElement
import com.scientianova.versatile.common.extensions.JsonBuilder
import com.scientianova.versatile.common.extensions.json
import com.scientianova.versatile.common.extensions.toResLoc
import net.minecraft.util.ResourceLocation

val ASSETS = hashMapOf<ResourceLocation, JsonElement>()

fun addAssetsJSON(location: ResourceLocation, json: JsonElement) {
    ASSETS[location] = json
}

fun addAssetsJSON(location: ResourceLocation, builder: JsonBuilder.() -> Unit) = addAssetsJSON(location, json(builder))
fun addAssetsJSON(location: String, json: JsonBuilder.() -> Unit) = addAssetsJSON(location.toResLoc(), json)