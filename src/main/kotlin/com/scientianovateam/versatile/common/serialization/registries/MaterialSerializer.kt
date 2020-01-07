package com.scientianovateam.versatile.common.serialization.registries

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.plus
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.math.Graph
import com.scientianovateam.versatile.common.registry.MATERIAL_PROPERTIES
import com.scientianovateam.versatile.common.serialization.IRegistrySerializer
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.properties.Property
import com.scientianovateam.versatile.velisp.convertToExpression
import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.PropertiesHolder
import com.scientianovateam.versatile.velisp.unresolved.Getter
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import net.minecraft.util.ResourceLocation

object MaterialSerializer : IRegistrySerializer<Material> {
    override fun read(json: JsonObject): Material {
        val properties = mutableMapOf<String, IEvaluated>()
        val loaded = mutableMapOf<Property, IUnresolved>()
        MATERIAL_PROPERTIES.forEach { (_, property) ->
            loaded[property] = if (json.has(property.name.toString()))
                convertToExpression(json.get(property.name.toString()))
            else property.default
        }
        val graph = Graph<Property>()
        loaded.forEach { (key, value) ->
            value.find(Getter::class.java) { it.name.startsWith("mat/") }.forEach {
                val iterator = it.name.drop(4).split('/').iterator()
                graph.add(findProperty(iterator, iterator.next().toResLocV())
                        ?: error("No such property with name ${it.name}"), key)
            }
        }
        val ordered = graph.topologicalSort() ?: error("Getter cycle")
        val propertiesHolder = PropertiesHolder(properties)
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

    private fun findProperty(iterator: Iterator<String>, currentName: ResourceLocation): Property? = MATERIAL_PROPERTIES[currentName]
            ?: if (iterator.hasNext()) findProperty(iterator, currentName + "/${iterator.next()}") else null

    override fun write(obj: Material): JsonObject {
        TODO("not implemented")
    }
}