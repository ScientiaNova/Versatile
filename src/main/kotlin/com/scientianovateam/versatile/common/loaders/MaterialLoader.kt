package com.scientianovateam.versatile.common.loaders

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.scientianovateam.versatile.common.extensions.getArrayOrNull
import com.scientianovateam.versatile.common.extensions.getStringOrNull
import com.scientianovateam.versatile.common.loaders.internal.cascadeJsons
import com.scientianovateam.versatile.common.loaders.internal.earlyResources
import com.scientianovateam.versatile.common.serialization.registries.MaterialSerializer
import com.scientianovateam.versatile.materialsystem.lists.Materials
import java.util.*


fun loadMaterials() {
    val jsonSets = mutableListOf<MutableList<JsonObject>>()
    fun add(json: JsonObject) {
        val jsonNames = names(json)
        jsonSets.forEach { list ->
            list.forEach {
                if (!Collections.disjoint(jsonNames, names(it))) {
                    list.add(json)
                    return
                }
            }
        }
        jsonSets.add(mutableListOf(json))
    }
    earlyResources.loadJsons("registries/materials").forEach { add(it) }
    val materials = jsonSets.map(::cascadeJsons)
    // TODO We need to sort the materials by their dependencies now
    materials.map(MaterialSerializer::read).forEach(Materials::add)
}

private fun names(json: JsonObject): List<String> {
    val result = mutableListOf<String>()
    json.getStringOrNull("name")?.let {
        result.add(it.toLowerCase())
    }
    json.getArrayOrNull("alternative_names")?.let { array ->
        array.forEach {
            if (it is JsonPrimitive) result.add(it.asString.toLowerCase())
        }
    }
    return result
}