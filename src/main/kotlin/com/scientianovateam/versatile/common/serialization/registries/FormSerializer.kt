package com.scientianovateam.versatile.common.serialization.registries

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.plus
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.math.Graph
import com.scientianovateam.versatile.common.registry.FORM_PROPERTIES
import com.scientianovateam.versatile.common.serialization.IRegistrySerializer
import com.scientianovateam.versatile.materialsystem.lists.Materials
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.properties.Property
import com.scientianovateam.versatile.velisp.BoolType
import com.scientianovateam.versatile.velisp.convertToExpression
import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.PropertiesHolder
import com.scientianovateam.versatile.velisp.unresolved.Getter
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import net.minecraft.util.ResourceLocation

object FormSerializer : IRegistrySerializer<Form> {
    override fun read(json: JsonObject): Form {
        val properties = mutableMapOf<Material, Map<String, IEvaluated>>()
        val loaded = mutableMapOf<Property, IUnresolved>()
        FORM_PROPERTIES.forEach { (_, property) ->
            loaded[property] = if (json.has(property.name))
                convertToExpression(json.get(property.name))
            else property.default
        }
        val graph = Graph<Property>()
        loaded.forEach { (key, value) ->
            value.find(Getter::class.java) { it.name.startsWith("form/") }.forEach {
                val iterator = it.name.drop(5).split('/').iterator()
                graph.add(findProperty(iterator, iterator.next().toResLocV())
                        ?: error("No such property with name ${it.name}"), key)
            }
        }
        val ordered = graph.topologicalSort() ?: error("Getter cycle")
        Materials.all.forEach { material ->
            val localProperties = mutableMapOf<String, IEvaluated>()
            val formProperties = PropertiesHolder(localProperties)
            val materialProperties = PropertiesHolder(material.properties)
            ordered.forEach { property ->
                val value = loaded[property] ?: error("Impossible")
                val evaluated = value.resolve(mapOf("form" to formProperties, "mat" to materialProperties)).evaluate()
                if (!(evaluated.type subtypes property.type)) error("Evaluated property $property of wrong type (Expected ${property.type.name} got ${evaluated.type.name})")
                val valid = property.valid.resolve(localProperties + ("it" to evaluated)).evaluate()
                if (valid !is BoolValue) error("Validity check for $property returned a ${valid.type} instead of a boolean")
                if (!valid.value) error("Validity check failed for $property, value ${evaluated.value}")
                localProperties[property.name] = evaluated
            }

            // Special case to avoid wasting memory on forms that will not be generated
            if (!(localProperties["generate"]?.value as? BoolValue
                            ?: error("Form predicate did not return a boolean")).value)
                return@forEach

            properties[material] = localProperties
        }

        return Form(properties)
    }

    private fun findProperty(iterator: Iterator<String>, currentName: ResourceLocation): Property? = FORM_PROPERTIES[currentName]
            ?: if (iterator.hasNext()) findProperty(iterator, currentName + "/${iterator.next()}") else null

    override fun write(obj: Form): JsonObject {
        TODO("not implemented")
    }
}