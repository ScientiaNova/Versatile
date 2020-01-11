package com.scientianovateam.versatile.common.serialization.registries

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.math.Graph
import com.scientianovateam.versatile.common.registry.MATERIAL_PROPERTIES
import com.scientianovateam.versatile.common.serialization.IRegistrySerializer
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.properties.Property
import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.StructValue
import com.scientianovateam.versatile.velisp.toExpression
import com.scientianovateam.versatile.velisp.unresolved.Getter
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

object MaterialSerializer : IRegistrySerializer<Material> {
    override fun read(json: JsonObject): Material {
        val properties = mutableMapOf<String, IEvaluated>()
        val loaded = mutableMapOf<Property, IUnresolved>()
        MATERIAL_PROPERTIES.forEach { (_, property) ->
            loaded[property] = if (json.has(property.name.toString()))
                json.get(property.name.toString()).toExpression()
            else property.default
        }
        val graph = Graph<Property>()
        loaded.forEach { (key, value) ->
            value.find(Getter::class.java) { it.name.startsWith("mat/") }.forEach {
                graph.add(MATERIAL_PROPERTIES[it.name.drop(4).takeWhile { char -> char != '/' }]
                        ?: error("No such property with name ${it.name}"), key)
            }
        }
        val ordered = graph.topologicalSort() ?: error("Getter cycle")
        val propertiesHolder = StructValue(properties)
        ordered.forEach { property ->
            val value = loaded[property] ?: error("Impossible")
            val evaluated = value.resolve(mapOf("mat" to propertiesHolder)).evaluate()
            if (!(evaluated.type subtypes property.type)) error("Evaluated property $property of wrong type (Expected ${property.type} got ${evaluated.type})")
            val valid = property.valid.resolve(properties + ("it" to evaluated)).evaluate()
            if (valid !is BoolValue) error("Validity check for $property returned a ${valid.type} instead of a boolean")
            if (!valid.value) error("Validity check failed for $property, value ${evaluated.value}")
            properties[property.name.toString()] = evaluated
        }
        return Material(properties)
    }

    override fun write(obj: Material): JsonObject {
        TODO("not implemented")
    }
}