package com.scientianovateam.versatile.common.loaders

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.scientianovateam.versatile.common.extensions.getArrayOrNull
import com.scientianovateam.versatile.common.extensions.getStringOrNull
import com.scientianovateam.versatile.common.loaders.internal.cascadeJsons
import com.scientianovateam.versatile.common.loaders.internal.earlyResources
import com.scientianovateam.versatile.common.math.Graph
import com.scientianovateam.versatile.common.registry.MATERIAL_PROPERTIES
import com.scientianovateam.versatile.common.serialization.registries.MaterialSerializer
import com.scientianovateam.versatile.materialsystem.lists.Materials
import com.scientianovateam.versatile.velisp.convertJSON
import com.scientianovateam.versatile.velisp.evaluated.StringValue
import com.scientianovateam.versatile.velisp.functions.constructor.MaterialFunction
import com.scientianovateam.versatile.velisp.unevaluated.FunctionCall
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import com.scientianovateam.versatile.velisp.unresolved.evaluate
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

    fun get(name: String): JsonObject {
        jsonSets.forEach { list -> list.forEach { if (name in names(it)) return it } }
        error("Could not find material with name $name")
    }

    earlyResources.loadJsons("registries/materials").forEach { add(it) }
    val graph = Graph<JsonObject>()
    jsonSets.map(::cascadeJsons).forEach { material ->
        MATERIAL_PROPERTIES.forEach { property ->
            val value = if (material.has(property.name))
                convertJSON(material.get(property.name))
            else property.default
            materialDependencies(value).map(::get).forEach { graph.add(it, material) }
        }
    }
    val materials = graph.topologicalSort() ?: error("Material dependency cycle")
    materials.map(MaterialSerializer::read).forEach(Materials::add)
}

private fun materialDependencies(expression: IUnresolved, result: MutableList<String> = mutableListOf<String>()): MutableList<String> {
    if (expression is FunctionCall) {
        if (expression.function == MaterialFunction) {
            (expression.inputs.firstOrNull()?.evaluate() as? StringValue)?.let { result.add(it.value) }
        } else {
            expression.inputs.forEach { materialDependencies(it, result) }
        }
    }
    return result
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