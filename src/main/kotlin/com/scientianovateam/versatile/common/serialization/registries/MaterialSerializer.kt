package com.scientianovateam.versatile.common.serialization.registries

import com.google.gson.JsonObject
import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.math.Graph
import com.scientianovateam.versatile.common.registry.MATERIAL_PROPERTIES
import com.scientianovateam.versatile.common.serialization.IRegistrySerializer
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.velisp.convertJSON
import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

object MaterialSerializer : IRegistrySerializer<Material> {
    override fun read(json: JsonObject): Material {
        val properties = mutableMapOf<String, IEvaluated>()
        val loaded = mutableMapOf<String, IUnresolved>()
        MATERIAL_PROPERTIES.forEach { property ->
            loaded[property.name] = if (json.has(property.name))
                convertJSON(json.get(property.name))
            else property.default
        }
        val graph = Graph<String>()
        loaded.forEach { (key, value) ->
            value.dependencies().forEach { graph.add(it, key) }
        }
        val ordered = graph.topologicalSort() ?: error("Getter cycle")
        ordered.forEach { key ->
            val value = loaded[key] ?: error("Impossible")
            val evaluated = value.resolve(properties).evaluate()
            val property = MATERIAL_PROPERTIES[key.toResLoc(Versatile.MOD_ID)] ?: error("Impossible")
            if (evaluated.type != property.type) error("Evaluated property $key of wrong type (Expected ${property.type.name} got ${evaluated.type.name})")
            val valid = property.valid.resolve(properties.toMutableMap().apply { put("it", evaluated) }).evaluate()
            if (valid !is BoolValue) error("Validity check for $key returned a ${valid.type} instead of a boolean")
            if (!valid.value) error("Validity check failed for $key, value ${evaluated.value}")
        }
        return Material(properties)
    }

    override fun write(obj: Material): JsonObject {
        TODO("not implemented")
    }
}