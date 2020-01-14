package com.scientianovateam.versatile.materialsystem.serializers

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.math.Graph
import com.scientianovateam.versatile.materialsystem.lists.FORM_PROPERTIES
import com.scientianovateam.versatile.materialsystem.lists.MATERIALS
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.properties.Property
import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.StructValue
import com.scientianovateam.versatile.velisp.toExpression
import com.scientianovateam.versatile.velisp.unresolved.Getter
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved

object FormSerializer {
    fun read(json: JsonObject): Form {
        val properties = mutableMapOf<Material, Map<String, IEvaluated>>()
        val loaded = mutableMapOf<Property, IUnresolved>()
        FORM_PROPERTIES.forEach { property ->
            loaded[property] = if (json.has(property.name.toString()))
                json.get(property.name.toString()).toExpression()
            else property.default
        }
        val graph = Graph<Property>()
        loaded.forEach { (key, value) ->
            value.find(Getter::class.java) { it.name.startsWith("form/") }.forEach {
                graph.add(FORM_PROPERTIES[it.name.drop(5).takeWhile { char -> char != '/' }]
                        ?: error("No such property with name ${it.name}"), key)
            }
        }
        val ordered = graph.topologicalSort() ?: error("Getter cycle")
        MATERIALS.forEach { material ->
            val localProperties = mutableMapOf<String, IEvaluated>()
            val formProperties = StructValue(localProperties)
            val materialProperties = StructValue(material.properties)
            ordered.forEach { property ->
                val value = loaded[property] ?: error("Impossible")
                val evaluated = value.resolve(mapOf("form" to formProperties, "mat" to materialProperties)).evaluate()
                if (!(evaluated.type subtypes property.type)) error("Evaluated property $property of wrong type (Expected ${property.type} got ${evaluated.type})")
                val valid = property.valid.resolve(localProperties + ("it" to evaluated)).evaluate()
                if (valid !is BoolValue) error("Validity check for $property returned a ${valid.type} instead of a boolean")
                if (!valid.value) error("Validity check failed for $property, value ${evaluated.value}")
                localProperties[property.name.toString()] = evaluated
            }

            // Special case to avoid wasting memory on forms that will not be generated
            if (!(localProperties["generate"]?.value as? BoolValue
                            ?: error("Form predicate did not return a boolean")).value)
                return@forEach

            properties[material] = localProperties
        }

        return Form(properties)
    }
}